package com.kcals.jetloyalty.ui.password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChangePasswordScreen(
    onPasswordChange: (currentPassword: String, newPassword: String, confirmPassword: String) -> Unit
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Current Password Field
        PasswordTextField(
            value = currentPassword,
            label = "Current Password",
            onValueChange = { currentPassword = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // New Password Field
        PasswordTextField(
            value = newPassword,
            label = "New Password",
            onValueChange = { newPassword = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password Field
        PasswordTextField(
            value = confirmPassword,
            label = "Confirm Password",
            onValueChange = { confirmPassword = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Submit Button
        Button(
            onClick = { onPasswordChange(currentPassword, newPassword, confirmPassword) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Change Password")
        }
    }
}

// Custom composable for password input fields
@Composable
fun PasswordTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
fun ChangePasswordPage() {
    val viewModel: PasswordViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    var errorMessage = remember { mutableStateOf("") }
    var successMessage = remember { mutableStateOf("") }

    when(state) {
        PasswordState.Loading -> CircularProgressIndicator()
        is PasswordState.Error -> errorMessage.value = (state as PasswordState.Error).error
        is PasswordState.Success -> errorMessage.value = (state as PasswordState.Success).message
        else -> {}
    }

    ChangePasswordScreen { a, b, c ->
        viewModel.processIntent(PasswordIntent.EnterCurrentPassword(a))
        viewModel.processIntent(PasswordIntent.EnterCurrentPassword(b))
        viewModel.processIntent(PasswordIntent.EnterCurrentPassword(c))
        viewModel.processIntent(PasswordIntent.SubmitPassword)
        println("TEXT ERROR: ${errorMessage.value}")
        println("TEXT SUCCESS: ${successMessage.value}")
    }



}

@Preview(showBackground = true)
@Composable
fun ChangePasswordScreenPreview() {
    ChangePasswordPage()
}
