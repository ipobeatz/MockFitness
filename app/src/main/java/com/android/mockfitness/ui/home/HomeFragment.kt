package com.android.mockfitness.ui.home

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.mockfitness.R
import com.android.mockfitness.data.CalorieData
import com.android.mockfitness.data.MockDataSource.calorieData
import com.android.mockfitness.data.MockDataSource.pulseData
import com.android.mockfitness.data.MockDataSource.sleepQualityData
import com.android.mockfitness.data.MockDataSource.stepData
import com.android.mockfitness.data.PulseData
import com.android.mockfitness.data.StepData
import com.android.mockfitness.data.SleepQualityData
import com.android.mockfitness.databinding.FragmentHomeBinding
import com.android.mockfitness.ui.detail.DetailFragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataReadRequest
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment() {
    private lateinit var googleSignInAccount: GoogleSignInAccount
    private val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 10000
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setup()
        //sss()
        //getReadData()
        stepBarChart(stepData)
        calorieBarChart(calorieData)
        sleepChart(sleepQualityData)
        pulseChart(pulseData)
        binding.stepCardView.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_detailFragment)
        }
    }

    fun sleepChart(sleepQualityData: ArrayList<SleepQualityData>) {
        var SleepForADay = 0
        sleepQualityData.forEach {
            SleepForADay += it.sleepQualityForADay
        }
        val hours = SleepForADay / 60
        val minutes = SleepForADay % 60
        binding.sleepDataCountText.text = getString(R.string.sleep_time, "$hours saat $minutes ")

        binding.dayOfThesleepData.text = sleepQualityData.first().day


        val barData = BarData()

        val barChart = binding.sleepDataChart

        val entriesFilled = ArrayList<BarEntry>()
        val entriesEmpty = ArrayList<BarEntry>()

        for (i in sleepQualityData.indices) {
            entriesFilled.add(BarEntry(i.toFloat(),360f))
            entriesEmpty.add(BarEntry(i.toFloat(), 360f - sleepQualityData.get(i).sleepQualityForADay.toFloat()))
        }

        val dataSetFilled = BarDataSet(entriesFilled, "Filled")
        dataSetFilled.color =Color.parseColor("#8E44AD")  // Koyu mor
        dataSetFilled.setDrawValues(false)

        val dataSetEmpty = BarDataSet(entriesEmpty, "Empty")
        dataSetEmpty.color = Color.parseColor("#D2B4DE") // Açık mor
        dataSetEmpty.setDrawValues(false)

        val data = BarData(dataSetFilled, dataSetEmpty)
        data.barWidth = 0.9f

        barChart.data = data

        barChart.axisLeft.isEnabled = false
        barChart.axisRight.isEnabled = false
        barChart.xAxis.isEnabled = false
        barChart.legend.isEnabled = false
        barChart.description.isEnabled = false
        barChart.setDoubleTapToZoomEnabled(false)
        barChart.animateY(1000)
        barChart.invalidate()
    }

    private fun pulseChart(pulseData: ArrayList<PulseData>) {
        val chart = binding.chartOfPulse
        val entries = ArrayList<CandleEntry>()

        for (i in 0 until pulseData.size) {
            /*
            val minVal = (Math.random() * 30 + 20).toFloat() // 20 ile 50 arasında minimum
            val maxVal = (Math.random() * 30 + 70).toFloat() // 70 ile 100 arasında maksimum

             */
            val minVal = pulseData.get(i).minPulse
            val maxVal = pulseData.get(i).maxPulse
            entries.add(
                CandleEntry(
                    i.toFloat(),
                    maxVal.toFloat(),
                    minVal.toFloat(),
                    75f,
                    75f
                )
            )
        }

        val dataSet = CandleDataSet(entries, "Range")
        dataSet.setDrawIcons(false)
        dataSet.axisDependency = YAxis.AxisDependency.LEFT
        dataSet.shadowColor = Color.RED
        dataSet.shadowWidth = 3f
        dataSet.decreasingColor = Color.RED
        dataSet.decreasingPaintStyle = Paint.Style.FILL
        dataSet.increasingColor = Color.GREEN
        dataSet.increasingPaintStyle = Paint.Style.FILL
        dataSet.neutralColor = Color.TRANSPARENT
        dataSet.setDrawValues(false)

        val data = CandleData(dataSet)

        chart.data = data
        val barChart = binding.chartOfPulse
        chart.description.isEnabled = false
        chart.legend.isEnabled = false

        chart.xAxis.isEnabled = false
        chart.axisLeft.isEnabled = true
        chart.axisLeft.setDrawGridLines(false)
        chart.axisRight.isEnabled = false
        chart.axisLeft.setDrawLabels(false)
        chart.axisLeft.setDrawGridLines(false)
        chart.xAxis.setDrawGridLines(false)
        barChart.setDoubleTapToZoomEnabled(false)
        chart.invalidate()
    }

    fun stepBarChart(stepData: ArrayList<StepData>) {
        var stepForADay = 0
        stepData.forEach {
            stepForADay += it.step
        }
        binding.stepCountText.text = getString(R.string.step_a_day, stepForADay.toString())
        binding.dayOfTheChart.text = stepData.first().day


        val barData = BarData()
        barData.barWidth = 0.5f

        stepData.forEachIndexed { index, stepData ->
            val entries = ArrayList<BarEntry>()
            entries.add(BarEntry(index.toFloat(), stepData.step.toFloat()))

            val dataSet = BarDataSet(entries, "Bar $index")
            dataSet.color = Color.YELLOW
            dataSet.setDrawValues(false)  // Değerleri gizle

            barData.addDataSet(dataSet)
        }
        val barChart = binding.stepChart
        barChart.data = barData

        barChart.description.isEnabled = false
        barChart.legend.isEnabled = false
        barChart.axisLeft.isEnabled = false
        barChart.axisRight.isEnabled = false
        barChart.xAxis.isEnabled = false
        barChart.setDoubleTapToZoomEnabled(false)
        barChart.setFitBars(true)  // Barları sığdırmak için
        barChart.invalidate()

    }

    fun calorieBarChart(calorieDataList: ArrayList<CalorieData>) {
        var calorieBurnForADay = 0
        calorieDataList.forEach {
            calorieBurnForADay += it.calorieBurnForADay
        }
        binding.dayOfCalorieBurn.text = getString(R.string.calorie_burn_a_day, calorieBurnForADay.toString())
        binding.dayOfTheCalorieChart.text = calorieDataList.first().day
        val barData = BarData()
        barData.barWidth = 0.5f

        calorieDataList.forEachIndexed { index, stepData ->
            val entries = ArrayList<BarEntry>()
            entries.add(BarEntry(index.toFloat(), stepData.calorieBurnForADay.toFloat()))

            val dataSet = BarDataSet(entries, "Bar $index")
            dataSet.color = Color.YELLOW
            dataSet.setDrawValues(false)  // Değerleri gizle

            barData.addDataSet(dataSet)
        }
        val barChart = binding.chartOfCalorie
        barChart.data = barData

        barChart.description.isEnabled = false
        barChart.legend.isEnabled = false
        barChart.axisLeft.isEnabled = false
        barChart.axisRight.isEnabled = false
        barChart.xAxis.isEnabled = false
        barChart.setDoubleTapToZoomEnabled(false)
        barChart.setFitBars(true)  // Barları sığdırmak için
        barChart.invalidate()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sss() {
        // Read the data that's been collected throughout the past week.
        val endTime = LocalDateTime.now().atZone(ZoneId.systemDefault())
        val startTime = endTime.minusWeeks(1)
        Log.i("TAG", "Range Start: $startTime")
        Log.i("TAG", "Range End: $endTime")

        val readRequest =
            DataReadRequest.Builder()
                // The data request can specify multiple data types to return,
                // effectively combining multiple data queries into one call.
                // This example demonstrates aggregating only one data type.
                .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA)
                // Analogous to a "Group By" in SQL, defines how data should be
                // aggregated.
                // bucketByTime allows for a time span, whereas bucketBySession allows
                // bucketing by <a href="/fit/android/using-sessions">sessions</a>.
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime.toEpochSecond(), endTime.toEpochSecond(), TimeUnit.SECONDS)
                .build()

        readRequest
    }

    fun setup() {
        val fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ).build()

        googleSignInAccount =
            GoogleSignIn.getAccountForExtension(this.requireContext(), fitnessOptions)

        if (!GoogleSignIn.hasPermissions(googleSignInAccount, fitnessOptions)) {
            val REQUEST_OAUTH_REQUEST_CODE = 0
            GoogleSignIn.requestPermissions(
                this, REQUEST_OAUTH_REQUEST_CODE, googleSignInAccount, fitnessOptions
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
            println("mcmc -- " + resultCode)
            if (resultCode == Activity.RESULT_OK) {
                // İzinler başarıyla alındı, veri okuma işlemine başla
                getReadData()
            } else {
                getReadData()
                // İzinler alınamadı, hata işleme
            }
        }
    }

    fun getReadData() {
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.YEAR, 2024)
        calendar.set(Calendar.MONTH, Calendar.MARCH)
        calendar.set(Calendar.DAY_OF_MONTH, 25)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val startTime = calendar.timeInMillis

        calendar.set(Calendar.YEAR, 2024)
        calendar.set(Calendar.MONTH, Calendar.APRIL)
        calendar.set(Calendar.DAY_OF_MONTH, 2)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)

        val endTime = calendar.timeInMillis

        val readRequest = DataReadRequest.Builder()
            .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
            .build()

        Fitness.getHistoryClient(this.requireContext(), googleSignInAccount)
            .readData(readRequest)
            .addOnSuccessListener { response ->
                val dataSet = response.getDataSet(DataType.AGGREGATE_STEP_COUNT_DELTA)
                // Verileri işle
                println("mcmc --22< " + dataSet)
            }
            .addOnFailureListener { e ->
                println("mcmc --< " + e.message)
                // Hata işleme
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}