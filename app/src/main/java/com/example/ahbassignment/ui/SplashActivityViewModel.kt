package com.example.ahbassignment.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ahbassignment.data.model.RemoteConfig
import com.example.ahbassignment.usecase.RemoteConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Shaheer cs on 18/02/2022.
 */
@HiltViewModel
class SplashActivityViewModel @Inject constructor(
    private val remoteConfigUseCase: RemoteConfigUseCase
) : ViewModel() {
    private val _uiState = mutableStateOf(RemoteConfig())
    val uiState= _uiState

    init {
        checkFirebaseRemoteConfig()
    }

    private fun checkFirebaseRemoteConfig() {
        viewModelScope.launch {
            remoteConfigUseCase.invoke().collect {
                it?.let {
                    _uiState.value = it
                }
            }
        }
    }


}