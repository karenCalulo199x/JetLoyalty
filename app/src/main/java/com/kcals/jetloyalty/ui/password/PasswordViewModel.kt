package com.kcals.jetloyalty.ui.password

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PasswordViewModel(): ViewModel() {
    private val _state = MutableStateFlow<PasswordState>(PasswordState.Idle)
    val state: StateFlow<PasswordState> get() = _state

    var currentPassword = mutableStateOf("")
    var newPassword = mutableStateOf("")
    var confirmPassword = mutableStateOf("")

    fun processIntent(passwordIntent: PasswordIntent) {
        when(passwordIntent) {
            is PasswordIntent.EnterConfirmPassword -> confirmPassword.value = passwordIntent.confirmPassword
            is PasswordIntent.EnterCurrentPassword -> currentPassword.value = passwordIntent.currentPass
            is PasswordIntent.EnterNewPassword -> newPassword.value = passwordIntent.newPassword
            PasswordIntent.SubmitPassword -> validatePassword()
        }
    }

    private fun validatePassword() {
        viewModelScope.launch {
            _state.value = PasswordState.Loading
            when {
                currentPassword.value.isEmpty() ||
                currentPassword.value.isEmpty() ||
                currentPassword.value.isEmpty() -> _state.value = PasswordState.Error("Fields should not be empty")
                else -> _state.value = PasswordState.Success("Success!")
            }
        }
    }
}