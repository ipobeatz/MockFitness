package com.android.mockfitness.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.android.mockfitness.R
import com.android.mockfitness.data.PulseData
import com.android.mockfitness.data.StepData
import com.android.mockfitness.data.UserDataType
import com.android.mockfitness.databinding.FragmentDetailBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val detailViewModel =
            ViewModelProvider(this).get(DetailViewModel::class.java)

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
        binding.typeOfDataText.text = "saat"
        binding.typeOfDataText3.text = stepData.first().typeName
        binding.typeOfMaxDataText.text = stepData.first().typeName
        binding.typeOfMaxDataText2.text = stepData.first().typeName

        var averageStepOfDay = 0
        val maxStepOfDayAsHour = (stepData.maxByOrNull {
            it.step
        }?.step ?: 0).toString()
        val minStepOfDayAsHour = (stepData.minByOrNull {
            it.step
        }?.step ?: 0).toString()
        val counterOfRestingHour = (stepData.filter {
            it.step == 0
        }.size)
        val restingTimeCounter = stepData.filter {
            it.step < 200
        }.size
        val activeTimeCounter = stepData.filter {
            it.step > 700
        }.size

        val progressOfPulse =
            (restingTimeCounter / (activeTimeCounter + restingTimeCounter).toFloat()) * 100


        stepData.forEach {
            averageStepOfDay += it.step
        }
        binding.dataMinCounterText.text = minStepOfDayAsHour
        binding.dataMaxCounterText.text = maxStepOfDayAsHour
        binding.dataAvarageCounterText.text = (averageStepOfDay / stepData.size).toString()
        binding.dataCounterText.text = counterOfRestingHour.toString()
        binding.customProgressBar.progress = progressOfPulse.toInt()
        binding.detailTitleText.text = "Yoğunluk durumu"

        binding.secondDataText.text = restingTimeCounter.toString()
        binding.secondDataSecondText.text = activeTimeCounter.toString()
    }

    private fun setupPulseDataUI(pulseData: List<PulseData>) {
        var averageOfResting = 0
        var totalMaxPulse = 0
        var totalMinPulse = 0

        pulseData.forEach {
            averageOfResting += it.restingPulse
            totalMaxPulse += it.maxPulse
            totalMinPulse += it.minPulse
        }
        val lightPulseCounter = pulseData.filter {
            it.restingPulse < 80
        }.size
        val heavyPulseCounter = pulseData.filter {
            it.restingPulse > 85
        }.size
        val progressOfPulse =
            (lightPulseCounter / (heavyPulseCounter + lightPulseCounter).toFloat()) * 100

        binding.dataCounterText.text = (averageOfResting / pulseData.size).toString()
        binding.dataMaxCounterText.text = (pulseData.maxByOrNull {
            it.maxPulse
        }?.maxPulse ?: 0).toString()
        binding.dataMinCounterText.text = (pulseData.minByOrNull {
            it.minPulse
        }?.minPulse ?: 0).toString()

        binding.dataAvarageCounterText.text =
            ((totalMaxPulse.plus(totalMinPulse)) / (pulseData.size * 2)).toString()

        binding.detailTitleText.text = getString(R.string.pulse_title_text)

        binding.customProgressBar.progress = progressOfPulse.toInt()

        binding.secondDataText.text = lightPulseCounter.toString()
        binding.secondDataSecondText.text = heavyPulseCounter.toString()
    }
}
