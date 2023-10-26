package com.example.univallealtoque.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.univallealtoque.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.univallealtoque.UnivalleAlToqueScreen
import com.example.univallealtoque.model.RegisterModel
import com.example.univallealtoque.model.UserData
import com.example.univallealtoque.sign_in.RegisterState
import com.example.univallealtoque.sign_in.RegisterViewModel
import com.example.univallealtoque.sign_in.SignInState
import com.example.univallealtoque.sign_in.SignInViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,

    modifier: Modifier,
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var repeat_password by rememberSaveable { mutableStateOf("") }
    var navigateLogin = { navController.navigate(UnivalleAlToqueScreen.Login.name) }

    //Manage register state
    val viewModel: RegisterViewModel = viewModel()
    val registrationState by viewModel.state.collectAsState()

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {


            Text(
                text = stringResource(R.string.register_title),
                style = MaterialTheme.typography.displayLarge,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 60.dp)
            )

            OutlinedTextField(
                value = name,
                textStyle = TextStyle(
                    color = Color.Black
                ),
                onValueChange = { name = it },
                label = { Text(text = stringResource(id = R.string.register_name)) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()

            )

            OutlinedTextField(
                value = email,
                textStyle = TextStyle(
                    color = Color.Black
                ),
                onValueChange = { email = it },
                label = { Text(text = stringResource(id = R.string.register_email)) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()

            )

            OutlinedTextField(
                value = password,
                textStyle = TextStyle(
                    color = Color.Black
                ),
                onValueChange = { password = it },
                label = { Text(text = stringResource(id = R.string.register_password)) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),

                )

            OutlinedTextField(
                value = repeat_password,
                textStyle = TextStyle(
                    color = Color.Black
                ),
                onValueChange = { repeat_password = it },
                label = { Text(text = stringResource(id = R.string.register_repeat_password)) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),

                )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .height(52.dp)
                    .background(
                        color = Color.Red,
                        shape = RoundedCornerShape(12.dp) // Ajusta el valor para redondear las esquinas
                    )
            ) {
                Button(
                    onClick = {
                        val registerData = RegisterModel(name, email, password)
                        viewModel.registerUser(registerData)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.register_title),
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.White // Color del texto
                    )
                }
            }

//            // Mostramos un mensaje de registro exitoso o de error según el estado
//            when (registrationState) {
//                is RegisterState -> {
//                    // Registro exitoso, puedes mostrar un mensaje o navegar a otra pantalla
//                }
//                is SignInState.RegistrationError -> {
//                    // Hubo un error en el registro, puedes mostrar un mensaje de error
//                    // accediendo a registrationState.error
//                }
//            }

            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier = Modifier.clickable { navigateLogin() }
            ) {
                Text(
                    text = stringResource(R.string.register_registered),
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier
                        .padding(end = 16.dp)
                )

                Text(
                    text = stringResource(R.string.login_title),
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Red
                )
            }
        }
    }


}