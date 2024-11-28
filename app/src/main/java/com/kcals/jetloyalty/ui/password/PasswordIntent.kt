package com.kcals.jetloyalty.ui.password

sealed class PasswordIntent {
    data class EnterCurrentPassword(val currentPass: String): PasswordIntent()
    data class EnterNewPassword(val newPassword: String): PasswordIntent()
    data class EnterConfirmPassword(val confirmPassword: String): PasswordIntent()
    object SubmitPassword: PasswordIntent()
}