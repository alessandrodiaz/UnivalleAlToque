package com.example.univallealtoque.ui

import CustomAlertDialog
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.univallealtoque.R
import com.example.univallealtoque.user_account.UserPasswordModel
import androidx.navigation.NavController
import com.example.univallealtoque.UnivalleAlToqueScreen
import com.example.univallealtoque.model.RecoverPasswordModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoverPasswordScreen(
    navController: NavController,
    modifier: Modifier,
) {
    var email by remember { mutableStateOf("") }
    val userPasswordModel: UserPasswordModel = viewModel()
    val userPasswordState by userPasswordModel.state.collectAsState()

    var recoveryMessage by remember { mutableStateOf("") }
    var navigateGetCode = { navController.navigate(UnivalleAlToqueScreen.GetCode.name) }

    LaunchedEffect(userPasswordModel.recoveryMessage) {
        userPasswordModel.recoveryMessage.collect {
            recoveryMessage = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(
            text = stringResource(R.string.recoverpassword_title),
            style = MaterialTheme.typography.displayLarge,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 28.dp)
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

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {
                // Llama al método de recuperación de contraseña del ViewModell
                val userEmail = RecoverPasswordModel(email)
                val response = userPasswordModel.recoverPassword(userEmail)

                Log.d("response register: ", response.toString())

            },
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .height(52.dp)
                .background(
                    color = Color.Red,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Text(
                text = "Enviar",
                style = MaterialTheme.typography.displaySmall,
                color = Color.White,
            )
        }

        if (userPasswordState.isEmailSentSuccessfully && userPasswordState.isRequestSuccessful) {
            CustomAlertDialog(
                title = stringResource(id = R.string.recover_email_sent_title),
                message = stringResource(id = R.string.recover_email_sent),
                onDismiss = { userPasswordModel.resetState() }
            )
        }

        if (!userPasswordState.isEmailValid && userPasswordState.isRequestSuccessful) {
            CustomAlertDialog(
                title = stringResource(id = R.string.error),
                message = stringResource(id = R.string.recover_invalid_email),
                onDismiss={ userPasswordModel.resetState()}
            )
        }


        println("recovery message" + recoveryMessage)

        // Observa el mensaje de recuperación y muestra un mensaje en la interfaz de usuario
        if (recoveryMessage.isNotBlank()) {
            Text(
                text = recoveryMessage,
                color = if (recoveryMessage.contains("registrado")) Color.Red else Color.Green,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
