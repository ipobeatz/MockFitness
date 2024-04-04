package com.android.mockfitness.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.android.mockfitness.R
import com.android.mockfitness.data.entity.PulseData
import com.android.mockfitness.data.entity.StepData
import com.android.mockfitness.data.entity.UserDataType
import com.android.mockfitness.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment @Inject constructor() : Fragment() {
    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val selectedDataType = UserDataType.valueOf(arguments?.getString("clickedDataType") ?: "")
        if (selectedDataType == UserDataType.PULSE) {
            detailViewModel.getUserPulseData()

            viewLifecycleOwner.lifecycleScope.launch {
                detailViewModel.pulseData.collectLatest {
                    setupPulseDataUI(it)
                }
            }
        } else if (selectedDataType == UserDataType.STEP) {
            detailViewModel.getUserStepData()

            viewLifecycleOwner.lifecycleScope.launch {
                detailViewModel.stepData.collectLatest {
                    setupStepDataUI(it)
                }
            }
        }
        return root
    }

    private fun setupStepDataUI(stepData: List<StepData>) {
        if (stepData.isNotEmpty()) {
            val typeName = stepData.first().typeName
            binding.apply {
                typeOfDataText.text = "saat"
                typeOfDataText3.text = typeName
                typeOfMaxDataText.text = typeName
                typeOfMaxDataText2.text = typeName
                detailTitleText.text = "Yoğunluk durumu"

                val maxStep = stepData.maxOfOrNull { it.step } ?: 0
                val minStep = stepData.minOfOrNull { it.step } ?: 0
                val averageStep = stepData.sumOf { it.step } / stepData.size
                val restingHourCount = stepData.count { it.step == 0 }
                val restingTimeCount = stepData.count { it.step < 200 }
                val activeTimeCount = stepData.count { it.step > 700 }
                val progress =
                    (restingTimeCount.toFloat() / (activeTimeCount + restingTimeCount) * 100).toInt()

                dataMinCounterText.text = minStep.toString()
                dataMaxCounterText.text = maxStep.toString()
                dataAvarageCounterText.text = averageStep.toString()
                dataCounterText.text = restingHourCount.toString()
                customProgressBar.progress = progress
                secondDataText.text = restingTimeCount.toString()
                secondDataSecondText.text = activeTimeCount.toString()
            }
        }
    }

    private fun setupPulseDataUI(pulseData: List<PulseData>) {
        val dataSize = pulseData.size
        val averageOfResting = pulseData.sumOf { it.restingPulse } / dataSize
        val totalMaxPulse = pulseData.maxOfOrNull { it.maxPulse } ?: 0
        val totalMinPulse = pulseData.minOfOrNull { it.minPulse } ?: 0
        val averagePulse = (pulseData.sumOf { it.maxPulse + it.minPulse }) / (dataSize * 2)

        val lightPulseCounter = pulseData.count { it.restingPulse < 80 }
        val heavyPulseCounter = pulseData.count { it.restingPulse > 85 }
        val progressOfPulse =
            (lightPulseCounter.toFloat() / (heavyPulseCounter + lightPulseCounter) * 100).toInt()

        binding.apply {
            dataCounterText.text = averageOfResting.toString()
            dataMaxCounterText.text = totalMaxPulse.toString()
            dataMinCounterText.text = totalMinPulse.toString()
            dataAvarageCounterText.text = averagePulse.toString()
            detailTitleText.text = getString(R.string.pulse_title_text)
            customProgressBar.progress = progressOfPulse
            secondDataText.text = lightPulseCounter.toString()
            secondDataSecondText.text = heavyPulseCounter.toString()
        }
    }
}
