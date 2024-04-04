package com.android.mockfitness.di

import android.content.Context
import com.android.mockfitness.data.datasource.FitApiIntegration
import com.android.mockfitness.ui.home.chart.ConfigureChart
import com.android.mockfitness.ui.home.chart.GraphChart
import com.android.mockfitness.ui.home.chart.IConfigureChart
import com.android.mockfitness.ui.home.chart.IGraphChart
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HealthAppProvider {

    @Provides
    fun provideFitApiIntegration(@ApplicationContext context: Context): FitApiIntegration {
        return FitApiIntegration(context)
    }

    @Provides
    fun provideGraphChart(@ApplicationContext context: Context): IGraphChart {
        return GraphChart(context)
    }

    @Provides
    fun provideConfigureChart(): IConfigureChart {
        return ConfigureChart()
    }
}
