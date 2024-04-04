package com.android.mockfitness.data.entity

data class StandingData(
    val standingValue: Int,
    val hourOfDay: Int,
    val day: String,
    val typeName: String = "dakika"
)