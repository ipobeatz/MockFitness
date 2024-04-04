package com.android.mockfitness.ui.home.chart

import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.CandleData

interface IConfigureChart {
    fun configureCandleChart(chart: CandleStickChart, data: CandleData)
    fun configureBarChart(barChart: BarChart, barData: BarData)
}
