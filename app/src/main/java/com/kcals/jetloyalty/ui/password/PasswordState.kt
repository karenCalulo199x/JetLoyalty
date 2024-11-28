package com.kcals.jetloyalty.ui.password

sealed class PasswordState {
    data class Success(val message: String): PasswordState()
    data class Error(val error: String): PasswordState()
    data object Loading: PasswordState()
    data object Idle: PasswordState()
}