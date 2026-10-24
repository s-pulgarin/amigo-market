package co.edu.amigo.market.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.edu.amigo.market.ui.theme.AmigoMarketTheme
import co.edu.amigo.market.viewmodel.SessionViewModel

@Composable
fun LoginScreen(
    sessionVm: SessionViewModel, onLoginSuccess: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    LoginContent(
        email = email,
        password = password,
        emailError = sessionVm.emailError,
        passwordError = sessionVm.passwordError,
        onEmailChange = { email = it; sessionVm.clearErrors() },
        onPasswordChange = { password = it; sessionVm.clearErrors() },
        onLogin = { if (sessionVm.login(email, password)) onLoginSuccess() }
    )
}

// Sin ViewModel para poder usarla en el @Preview
@Composable
private fun LoginContent(
    email: String,
    password: String,
    emailError: String?,
    passwordError: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }
    val canLogin = email.isNotBlank() && password.isNotBlank()

    Surface(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Amigó Market",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                "Ingresa con tu correo institucional",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("Correo") },
                placeholder = { Text("nombre.apellido${SessionViewModel.DOMAIN}") },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
                isError = emailError != null,
                supportingText = emailError?.let { { Text(it) } },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            if (showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (showPassword) "Ocultar contraseña" else "Mostrar contraseña"
                        )
                    }
                },
                visualTransformation = if (showPassword) VisualTransformation.None
                else PasswordVisualTransformation(),
                isError = passwordError != null,
                supportingText = passwordError?.let { { Text(it) } },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { if (canLogin) onLogin() }),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onLogin,
                enabled = canLogin,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Iniciar sesión") }
        }
    }
}

@Preview(showBackground = true, name = "Login vacío")
@Composable
private fun LoginEmptyPreview() {
    AmigoMarketTheme {
        LoginContent("", "", null, null, {}, {}, {})
    }
}

@Preview(showBackground = true, name = "Login con errores")
@Composable
private fun LoginErrorPreview() {
    AmigoMarketTheme {
        LoginContent(
            email = "juan@gmail.com",
            password = "123",
            emailError = "Usa tu correo institucional ${SessionViewModel.DOMAIN}",
            passwordError = "La contraseña debe tener al menos ${SessionViewModel.MIN_PASSWORD} caracteres",
            onEmailChange = {}, onPasswordChange = {}, onLogin = {}
        )
    }
}
