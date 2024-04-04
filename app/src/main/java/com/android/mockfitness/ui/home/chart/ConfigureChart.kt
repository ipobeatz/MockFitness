package com.android.mockfitness.ui.home.chart

import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.CandleData
import javax.inject.Inject

class ConfigureChart @Inject constructor() : IConfigureChart {
    override fun configureCandleChart(chart: CandleStickChart, data: CandleData) {
        chart.apply {
            this.data = data
            description.isEnabled = false
            legend.isEnabled = false
            xAxis.isEnabled = false
            axisLeft.isEnabled = true
            axisLeft.setDrawGridLines(false)
            axisRight.isEnabled = false
            axisLeft.setDrawLabels(false)
            xAxis.setDrawGridLines(false)
            isDoubleTapToZoomEnabled = false
            invalidate()
        }
    }

    override fun configureBarChart(barChart: BarChart, barData: BarData) {
        barChart.apply {
            data = barData
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            xAxis.isEnabled = false
            legend.isEnabled = false
            description.isEnabled = false
            isDoubleTapToZoomEnabled = false
            animateY(1000)
            invalidate()
        }
    }
}
