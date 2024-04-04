package com.android.mockfitness.data.entity

data class PulseData(
    val minPulse: Int,
    val maxPulse: Int,
    val restingPulse: Int,
    val hourOfDay: Int,
    val day: String,
    val type: String = "BPM"
)