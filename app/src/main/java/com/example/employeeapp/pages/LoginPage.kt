package com.example.employeeapp.pages

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.employeeapp.AuthState
import com.example.employeeapp.AuthViewModel

@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by authViewModel.authState.observeAsState()
    val context = LocalContext.current
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val successMessage by authViewModel.message.observeAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                if (navController.previousBackStackEntry == null) {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true}
                        }
                    }

                else if (navController.currentDestination?.route != "profile") { //checks to avoid a loop
                    navController.navigate("profile")
                }
            }
            is AuthState.Error -> {
                showError = true
                errorMessage = (authState as AuthState.Error).message
            }
            else -> Unit
        }
    }

    val buttonColor by animateColorAsState(
        targetValue = if (authState == AuthState.Loading) Color.Gray else MaterialTheme.colorScheme.primary,
        animationSpec = tween(durationMillis = 300), label = ""
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Account Login", fontSize = 32.sp, color = Color.Black)

        Spacer(modifier = Modifier.height(16.dp))

        successMessage?.let {
            Text(text = it, color = Color.Green, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                authViewModel.login(email, password)
            },
            enabled = authState != AuthState.Loading,
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            if (authState == AuthState.Loading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            } else {
                Text(text = "Login")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.navigate("signup") }) {
            Text(text = "New User? Signup Here.")
        }

        if (showError) {
            Snackbar(
                action = {
                    TextButton(onClick = { showError = false }) {
                        Text(text = "Dismiss")
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = errorMessage)
            }
        }
    }
}
