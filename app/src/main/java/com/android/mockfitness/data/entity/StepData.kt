package com.android.mockfitness.data.entity

data class StepData(
    val step: Int,
    val hourOfDay: Int,
    val day: String,
    val typeName: String = "adım"
)