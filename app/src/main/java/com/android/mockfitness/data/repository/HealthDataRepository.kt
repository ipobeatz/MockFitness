package com.android.mockfitness.data.repository

import com.android.mockfitness.data.entity.CalorieData
import com.android.mockfitness.data.entity.PulseData
import com.android.mockfitness.data.entity.SleepQualityData
import com.android.mockfitness.data.entity.StepData
import com.android.mockfitness.data.entity.MovementData
import com.android.mockfitness.data.entity.StandingData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.Flow

interface HealthDataRepository {
    suspend fun getPulseData(): Flow<ArrayList<PulseData>>
    suspend fun getStepData(googleSignInAccount: GoogleSignInAccount): Flow<ArrayList<StepData>>
    suspend fun getCaloriesData(): Flow<ArrayList<CalorieData>>
    suspend fun getStandingData(): Flow<ArrayList<StandingData>>
    suspend fun getMovementData(): Flow<ArrayList<MovementData>>
    suspend fun getSleepingQualityData(): Flow<ArrayList<SleepQualityData>>
}
