package com.android.mockfitness.di

import com.android.mockfitness.data.datasource.HealthDataDataSource
import com.android.mockfitness.data.repository.HealthDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryProvider {

    @Binds
    abstract fun bindHealthDataProvider(healthDataDataSource: HealthDataDataSource): HealthDataRepository
}
