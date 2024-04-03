package com.android.mockfitness.data

object MockDataSource {
    val stepData = arrayListOf(
        StepData(39, 0, "28 Mart"),
        StepData(123, 1, "28 Mart"),
        StepData(2412, 2, "28 Mart"),
        StepData(0, 3, "28 Mart"),
        StepData(0, 4, "28 Mart"),
        StepData(0, 5, "28 Mart"),
        StepData(0, 6, "28 Mart"),
        StepData(0, 7, "28 Mart"),
        StepData(0, 8, "28 Mart"),
        StepData(125, 9, "28 Mart"),
        StepData(2865, 10, "28 Mart"),
        StepData(504, 11, "28 Mart"),
        StepData(39, 12, "28 Mart"),
        StepData(1239, 13, "28 Mart"),
        StepData(343, 14, "28 Mart"),
        StepData(25, 15, "28 Mart"),
        StepData(66, 16, "28 Mart"),
        StepData(0, 17, "28 Mart"),
        StepData(0, 18, "28 Mart"),
        StepData(0, 19, "28 Mart"),
        StepData(0, 20, "28 Mart"),
        StepData(0, 21, "28 Mart"),
        StepData(0, 22, "28 Mart"),
        StepData(0, 23, "28 Mart"),
        StepData(0, 0, "29 Mart"),
    )

    val calorieData = arrayListOf(
        CalorieData(147, 0, "28 Mart"),
        CalorieData(203, 1, "28 Mart"),
        CalorieData(518, 2, "28 Mart"),
        CalorieData(10, 3, "28 Mart"),
        CalorieData(0, 4, "28 Mart"),
        CalorieData(0, 5, "28 Mart"),
        CalorieData(0, 6, "28 Mart"),
        CalorieData(0, 7, "28 Mart"),
        CalorieData(0, 8, "28 Mart"),
        CalorieData(203, 9, "28 Mart"),
        CalorieData(612, 10, "28 Mart"),
        CalorieData(169, 11, "28 Mart"),
        CalorieData(201, 12, "28 Mart"),
        CalorieData(351, 13, "28 Mart"),
        CalorieData(262, 14, "28 Mart"),
        CalorieData(142, 15, "28 Mart"),
        CalorieData(89, 16, "28 Mart"),
        CalorieData(0, 17, "28 Mart"),
        CalorieData(0, 18, "28 Mart"),
        CalorieData(0, 19, "28 Mart"),
        CalorieData(0, 20, "28 Mart"),
        CalorieData(0, 21, "28 Mart"),
        CalorieData(0, 22, "28 Mart"),
        CalorieData(0, 23, "28 Mart"),
        CalorieData(0, 0, "29 Mart"),
    )


    val sleepQualityData = arrayListOf(
        SleepQualityData(321, 0, "28 Mart"),
        SleepQualityData(103, 6, "28 Mart"),
        SleepQualityData(0, 12, "28 Mart"),
        SleepQualityData(0, 18, "28 Mart"),
    )

    val pulseData = arrayListOf(
        PulseData(60, 100, 0, "28 Mart"),
        PulseData(70, 85, 2, "28 Mart"),
        PulseData(63, 95, 4, "28 Mart"),
        PulseData(46, 110, 6, "28 Mart"),
        PulseData(57, 120, 8, "28 Mart"),
        PulseData(56, 130, 10, "28 Mart"),
        PulseData(78, 115, 12, "28 Mart"),
        PulseData(96, 125, 14, "28 Mart"),
        PulseData(76, 119, 16, "28 Mart"),
        PulseData(78, 102, 18, "28 Mart"),
        PulseData(54, 80, 20, "28 Mart"),
        PulseData(45, 88, 22, "28 Mart"),
        PulseData(80, 102, 24, "29 Mart"),
    )
}

data class StepData(val step: Int, val hourOfDay: Int, val day: String)
data class CalorieData(val calorieBurnForADay: Int, val hourOfDay: Int, val day: String)
data class SleepQualityData(val sleepQualityForADay: Int, val hourOfDay: Int, val day: String)
data class PulseData(val minPulse: Int, val maxPulse: Int, val hourOfDay: Int, val day: String)