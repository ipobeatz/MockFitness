package com.android.mockfitness.ui.home

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.mockfitness.MainActivity
import com.android.mockfitness.R
import com.android.mockfitness.data.entity.CalorieData
import com.android.mockfitness.data.entity.MovementData
import com.android.mockfitness.data.entity.PulseData
import com.android.mockfitness.data.entity.SleepQualityData
import com.android.mockfitness.data.entity.StandingData
import com.android.mockfitness.data.entity.StepData
import com.android.mockfitness.data.entity.UserDataType
import com.android.mockfitness.databinding.FragmentHomeBinding
import com.android.mockfitness.ui.home.chart.ConfigureChart
import com.android.mockfitness.ui.home.chart.GraphChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor() : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private val binding get() = _binding!!

    @Inject
    lateinit var graphChart: GraphChart

    @Inject
    lateinit var configureChart: ConfigureChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observePulseData()
        observeStepData()
        observeCaloriesData()
        observeMovementData()
        observeStandingData()
        observeSleepingQuality()
        playClickAnimation()

        (activity as? MainActivity)?.googleSignInAccount?.observe(viewLifecycleOwner) { account ->
            viewModel.getUserStepData(account)
        }

        binding.stepCardView.setOnClickListener {
            val action =
                HomeFragmentDirections.actionNavigationHomeToDetailFragment(UserDataType.STEP.name)
            findNavController().navigate(action)
        }
        binding.stepChart.setOnClickListener {
            val action =
                HomeFragmentDirections.actionNavigationHomeToDetailFragment(UserDataType.STEP.name)
            findNavController().navigate(action)
        }
        binding.pulseCardView.setOnClickListener {
            val action =
                HomeFragmentDirections.actionNavigationHomeToDetailFragment(UserDataType.PULSE.name)
            findNavController().navigate(action)
        }
        binding.chartOfPulse.setOnClickListener {
            val action =
                HomeFragmentDirections.actionNavigationHomeToDetailFragment(UserDataType.PULSE.name)
            findNavController().navigate(action)
        }
    }

    private fun playClickAnimation() {
        val animation = AnimationUtils.loadAnimation(this.requireContext(), R.anim.click_animation)
        binding.clickableCardBPM?.startAnimation(animation)
        binding.clickableCardStep?.startAnimation(animation)
    }

    private fun observePulseData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pulseData.collect { pulseDataList ->
                pulseChart(pulseDataList)
            }
        }
    }

    private fun observeStepData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stepData.collect { stepDataList ->
                stepBarChart(stepDataList)
            }
        }
    }

    private fun observeCaloriesData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.caloriesData.collect { caloriesDataList ->
                calorieBarChart(caloriesDataList)
            }
        }
    }

    private fun observeMovementData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movementData.collect { movementDataList ->
                movementChart(movementDataList)
            }
        }
    }

    private fun observeStandingData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.standingData.collect { standingDataList ->
                standingChart(standingDataList)
            }
        }
    }

    private fun observeSleepingQuality() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sleepingQuality.collect { sleepingQualityList ->
                sleepChart(sleepingQualityList)
            }
        }
    }

    private fun stepBarChart(stepData: List<StepData>) {
        if (stepData.isNotEmpty()) {
            val stepForADay = stepData.sumOf { it.step }
            binding.stepCountText.text = getString(R.string.step_a_day, stepForADay.toString())
            binding.dayOfTheChart.text = stepData.first().day

            val barData = BarData()
            barData.barWidth = 0.5f

            stepData.forEachIndexed { index, stepData ->
                val dataSet = graphChart.createBarDataSet(index, stepData.step)
                barData.addDataSet(dataSet)
            }

            configureChart.configureBarChart(binding.stepChart, barData)
        }
    }

    private fun sleepChart(sleepQualityData: List<SleepQualityData>) {
        if (sleepQualityData.isNotEmpty()) {
            val sleepForADay = sleepQualityData.sumOf { it.sleepQualityForADay }
            val (hours, minutes) = calculateTime(sleepForADay)
            binding.sleepDataCountText.text =
                getString(R.string.sleep_time, "$hours saat $minutes")
            binding.dayOfThesleepData.text = sleepQualityData.first().day

            val entriesFilled = ArrayList<BarEntry>()
            val entriesEmpty = ArrayList<BarEntry>()

            sleepQualityData.forEachIndexed { index, data ->
                entriesFilled.add(BarEntry(index.toFloat(), 360f))
                entriesEmpty.add(
                    BarEntry(
                        index.toFloat(), 360f - data.sleepQualityForADay.toFloat()
                    )
                )
            }

            val dataSetFilled =
                graphChart.createBarDataSet(entriesFilled, "Filled", Color.parseColor("#5871F6"))
            val dataSetEmpty =
                graphChart.createBarDataSet(entriesEmpty, "Empty", Color.parseColor("#353D64"))

            val barData = BarData(dataSetFilled, dataSetEmpty).apply {
                barWidth = 0.9f
            }

            configureChart.configureBarChart(binding.sleepDataChart, barData)
        }
    }

    private fun calculateTime(minutes: Int): Pair<Int, Int> {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return Pair(hours, remainingMinutes)
    }

    private fun pulseChart(pulseData: List<PulseData>) {
        if (pulseData.isNotEmpty()) {
            val chart = binding.chartOfPulse
            val entries = ArrayList<CandleEntry>()

            pulseData.forEachIndexed { index, data ->
                binding.dayOfPulse.text = data.restingPulse.toString()
                binding.dayOfThePulseChart.text = data.day

                entries.add(
                    CandleEntry(
                        index.toFloat(), data.maxPulse.toFloat(), data.minPulse.toFloat(), 75f, 75f
                    )
                )
            }

            val dataSet = graphChart.createCandleDataSet(entries, "Range")
            val candleData = CandleData(dataSet)
            configureChart.configureCandleChart(chart, candleData)
        }
    }

    private fun calorieBarChart(calorieDataList: List<CalorieData>) {
        if (calorieDataList.isNotEmpty()) {
            val calorieBurnForADay = calorieDataList.sumOf { it.calorieBurnForADay }
            binding.dayOfCalorieBurn.text =
                getString(R.string.calorie_burn_a_day, calorieBurnForADay.toString())
            binding.dayOfTheCalorieChart.text = calorieDataList.first().day

            val barData = BarData()
            barData.barWidth = 0.5f

            calorieDataList.forEachIndexed { index, calorieData ->
                val entries =
                    arrayListOf(BarEntry(index.toFloat(), calorieData.calorieBurnForADay.toFloat()))
                val dataSet =
                    graphChart.createBarCalorieDataSet(entries, "Bar $index", R.color.yellow)
                barData.addDataSet(dataSet)
            }

            configureChart.configureBarChart(binding.chartOfCalorie, barData)
        }
    }

    private fun standingChart(standingData: List<StandingData>) {
        if (standingData.isNotEmpty()) {
            val stepForADay = standingData.sumOf { it.standingValue }
            binding.standingTime.text = getString(
                R.string.movement_text,
                stepForADay.toString(),
                standingData.first().typeName
            )
            binding.standingDate.text = standingData.first().day

            val barData = BarData()
            barData.barWidth = 0.5f

            standingData.forEachIndexed { index, stepData ->
                val entries =
                    arrayListOf(BarEntry(index.toFloat(), stepData.standingValue.toFloat()))
                val dataSet =
                    graphChart.createBarStandingDataSet(entries, "Bar $index", R.color.green)
                barData.addDataSet(dataSet)
            }

            configureChart.configureBarChart(binding.standingChart, barData)
        }
    }

    private fun movementChart(movementData: List<MovementData>) {
        if (movementData.isNotEmpty()) {
            val stepForADay = movementData.sumOf { it.movementValue }
            binding.movementTime.text =
                getString(
                    R.string.movement_text,
                    stepForADay.toString(),
                    movementData.first().typeName
                )
            binding.movementDate.text = movementData.first().day

            val barData = BarData()
            barData.barWidth = 0.5f

            movementData.forEachIndexed { index, stepData ->
                val entries =
                    arrayListOf(BarEntry(index.toFloat(), stepData.movementValue.toFloat()))
                val dataSet =
                    graphChart.createBarMovementDataSet(entries, "Bar $index", R.color.blue)
                barData.addDataSet(dataSet)
            }

            configureChart.configureBarChart(binding.movementChart, barData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
