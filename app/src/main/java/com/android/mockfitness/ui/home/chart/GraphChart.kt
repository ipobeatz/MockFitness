package com.android.mockfitness.ui.home.chart

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GraphChart @Inject constructor(@ApplicationContext private val context: Context) :
    IGraphChart {

    override fun createBarMovementDataSet(
        entries: List<BarEntry>, label: String, colorResId: Int
    ): BarDataSet {
        return BarDataSet(entries, label).apply {
            color = ContextCompat.getColor(context, colorResId)
            setDrawValues(false)
        }
    }

    override fun createBarStandingDataSet(
        entries: List<BarEntry>, label: String, colorResId: Int
    ): BarDataSet {
        return BarDataSet(entries, label).apply {
            color = ContextCompat.getColor(context, colorResId)
            setDrawValues(false)
        }
    }

    override fun createBarCalorieDataSet(
        entries: List<BarEntry>, label: String, colorResId: Int
    ): BarDataSet {
        return BarDataSet(entries, label).apply {
            color = ContextCompat.getColor(context, colorResId)
            setDrawValues(false)
        }
    }

    override fun createCandleDataSet(entries: List<CandleEntry>, label: String): CandleDataSet {
        return CandleDataSet(entries, label).apply {
            setDrawIcons(false)
            axisDependency = YAxis.AxisDependency.LEFT
            shadowColor = Color.RED
            shadowWidth = 3f
            decreasingColor = Color.RED
            decreasingPaintStyle = Paint.Style.FILL
            increasingColor = Color.GREEN
            increasingPaintStyle = Paint.Style.FILL
            neutralColor = Color.TRANSPARENT
            setDrawValues(false)
        }
    }

    override fun createBarDataSet(
        entries: ArrayList<BarEntry>, label: String, color: Int
    ): BarDataSet {
        return BarDataSet(entries, label).apply {
            this.color = color
            setDrawValues(false)
        }
    }

    override fun createBarDataSet(index: Int, stepCount: Int): BarDataSet {
        val entries = arrayListOf(BarEntry(index.toFloat(), stepCount.toFloat()))
        return BarDataSet(entries, "Bar $index").apply {
            color = Color.YELLOW
            setDrawValues(false)
        }
    }
}
