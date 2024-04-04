package com.android.mockfitness.ui.home.chart

import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry

interface IGraphChart {
    fun createBarMovementDataSet(
        entries: List<BarEntry>, label: String, colorResId: Int
    ): BarDataSet

    fun createBarStandingDataSet(
        entries: List<BarEntry>, label: String, colorResId: Int
    ): BarDataSet

    fun createBarCalorieDataSet(
        entries: List<BarEntry>, label: String, colorResId: Int
    ): BarDataSet

    fun createCandleDataSet(entries: List<CandleEntry>, label: String): CandleDataSet

    fun createBarDataSet(
        entries: ArrayList<BarEntry>, label: String, color: Int
    ): BarDataSet

    fun createBarDataSet(index: Int, stepCount: Int): BarDataSet
}
