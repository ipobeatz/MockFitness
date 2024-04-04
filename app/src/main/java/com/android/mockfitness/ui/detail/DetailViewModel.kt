package com.android.mockfitness.ui.detail

import androidx.lifecycle.ViewModel
import com.android.mockfitness.data.mockdata.pulseData as mockPulseData
import com.android.mockfitness.data.mockdata.stepData as mockStepData
import com.android.mockfitness.data.entity.PulseData
import com.android.mockfitness.data.entity.StepData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailViewModel : ViewModel() {

    private val _pulseData: MutableStateFlow<List<PulseData>> = MutableStateFlow(listOf())
    val pulseData: StateFlow<List<PulseData>> = _pulseData

    private val _stepData: MutableStateFlow<List<StepData>> = MutableStateFlow(listOf())
    val stepData: StateFlow<List<StepData>> = _stepData

    fun getUserPulseData() {
        _pulseData.value = mockPulseData
    }


    fun getUserStepData() {
        _stepData.value = mockStepData
    }
}
