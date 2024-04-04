package com.android.mockfitness.data.entity

data class MovementData(
    val movementValue: Int,
    val hourOfDay: Int,
    val day: String,
    val typeName: String = "dakika"
)