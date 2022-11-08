package com.brandyodhiambo.mynote.feature_auth.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandyodhiambo.mynote.feature_auth.domain.usecase.AutoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val autoLoginUseCase: AutoLoginUseCase) :
    ViewModel() {

    private val _eventState = mutableStateOf(false)
    val eventState: State<Boolean> = _eventState

    init {
        autoLoginUser()
    }

    private fun autoLoginUser() {
        viewModelScope.launch {
            _eventState.value = autoLoginUseCase.autoLogin()
        }
    }
}