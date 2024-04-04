package com.android.mockfitness.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.mockfitness.data.entity.CalorieData
import com.android.mockfitness.data.entity.MovementData
import com.android.mockfitness.data.entity.PulseData
import com.android.mockfitness.data.entity.SleepQualityData
import com.android.mockfitness.data.entity.StandingData
import com.android.mockfitness.data.entity.StepData
import com.android.mockfitness.data.repository.HealthDataRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val healthDataRepository: HealthDataRepository
) : ViewModel() {

    private val _pulseData: MutableStateFlow<List<PulseData>> = MutableStateFlow(listOf())
    val pulseData: StateFlow<List<PulseData>> = _pulseData

    private val _stepData: MutableStateFlow<List<StepData>> = MutableStateFlow(listOf())
    val stepData: StateFlow<List<StepData>> = _stepData

    private val _caloriesData: MutableStateFlow<List<CalorieData>> = MutableStateFlow(listOf())
    val caloriesData: StateFlow<List<CalorieData>> = _caloriesData

    private val _movementData: MutableStateFlow<List<MovementData>> = MutableStateFlow(listOf())
    val movementData: StateFlow<List<MovementData>> = _movementData

    private val _standingData: MutableStateFlow<List<StandingData>> = MutableStateFlow(listOf())
    val standingData: StateFlow<List<StandingData>> = _standingData

    private val _sleepingQuality: MutableStateFlow<List<SleepQualityData>> =
        MutableStateFlow(listOf())
    val sleepingQuality: StateFlow<List<SleepQualityData>> = _sleepingQuality

    init {
        getUserPulseData()
        getCaloriesData()
        getMovementData()
        getStandingData()
        getSleepingQualityData()
    }

    private fun getUserPulseData() {
        viewModelScope.launch {
            healthDataRepository.getPulseData().collectLatest {
                _pulseData.value = it
            }
        }
    }

    fun getUserStepData(googleSignInAccount: GoogleSignInAccount) {
        viewModelScope.launch {
            healthDataRepository.getStepData(googleSignInAccount).collectLatest {
                _stepData.value = it
            }
        }
    }

    private fun getCaloriesData() {
        viewModelScope.launch {
            healthDataRepository.getCaloriesData().collectLatest {
                _caloriesData.value = it
            }
        }
    }

    private fun getMovementData() {
        viewModelScope.launch {
            healthDataRepository.getMovementData().collectLatest {
                _movementData.value = it
            }
        }
    }

    private fun getStandingData() {
        viewModelScope.launch {
            healthDataRepository.getStandingData().collectLatest {
                _standingData.value = it
            }
        }
    }

    private fun getSleepingQualityData() {
        viewModelScope.launch {
            healthDataRepository.getSleepingQualityData().collectLatest {
                _sleepingQuality.value = it
            }
        }
    }
}
