package com.android.mockfitness.data.datasource

import com.android.mockfitness.data.entity.CalorieData
import com.android.mockfitness.data.entity.MovementData
import com.android.mockfitness.data.entity.PulseData
import com.android.mockfitness.data.entity.SleepQualityData
import com.android.mockfitness.data.entity.StandingData
import com.android.mockfitness.data.entity.StepData
import com.android.mockfitness.data.mockdata.calorieData
import com.android.mockfitness.data.mockdata.pulseData
import com.android.mockfitness.data.mockdata.sleepQualityData
import com.android.mockfitness.data.mockdata.stepData
import com.android.mockfitness.data.mockdata.movementData
import com.android.mockfitness.data.mockdata.standingData
import com.android.mockfitness.data.repository.HealthDataRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HealthDataDataSource @Inject constructor(private val fitApiIntegration: FitApiIntegration) :
    HealthDataRepository {
    override suspend fun getPulseData(): Flow<ArrayList<PulseData>> = flow {
        emit(
            pulseData
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun getStepData(googleSignInAccount: GoogleSignInAccount): Flow<ArrayList<StepData>> =
        flow {
            //TODO: get value from fit api and parse and return value
            /*val responseFitApi =
                fitApiIntegration.readStepCountData(googleSignInAccount = googleSignInAccount)
            if (responseFitApi.dataSets.isNotEmpty()) {

            }
             */
            emit(
                stepData
            )
        }.flowOn(Dispatchers.IO)

    override suspend fun getCaloriesData(): Flow<ArrayList<CalorieData>> = flow {
        emit(
            calorieData
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun getStandingData(): Flow<ArrayList<StandingData>> = flow {
        emit(
            standingData
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun getMovementData(): Flow<ArrayList<MovementData>> = flow {
        emit(
            movementData
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun getSleepingQualityData(): Flow<ArrayList<SleepQualityData>> = flow {
        emit(
            sleepQualityData
        )
    }.flowOn(Dispatchers.IO)
}
