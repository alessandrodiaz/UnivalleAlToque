package com.example.univallealtoque.ui

import CustomAlertDialog
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.univallealtoque.R
import com.example.univallealtoque.user_account.RecoverPasswordViewModel
import androidx.navigation.NavController
import com.example.univallealtoque.UnivalleAlToqueScreen
import com.example.univallealtoque.data.DataStoreSingleton
import com.example.univallealtoque.model.LockoutModel
import com.example.univallealtoque.model.RecoverPasswordModel
import com.example.univallealtoque.model.UserDataResponseExpress
import com.example.univallealtoque.user_account.LockoutUserViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoverPasswordScreen(
    navController: NavController,
    modifier: Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    val recoverPasswordViewModel: RecoverPasswordViewModel = viewModel()
    val userPasswordState by recoverPasswordViewModel.state.collectAsState()

    val lockoutUserViewModel: LockoutUserViewModel = viewModel()
    val lockoutUserState by lockoutUserViewModel.state.collectAsState()
    var expirationDialog by remember { mutableStateOf(false) }
    var lastAttemptDialog by remember { mutableStateOf(false) }
    var suspendDialog by remember {
        mutableStateOf(false)
    }
    var wrongCode by remember {
        mutableStateOf(false)
    }


    var recoveryMessage by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var navigateNewPassword = { navController.navigate(UnivalleAlToqueScreen.NewPassword.name) }
    var emailSent by remember { mutableStateOf(false) }
    var failedAttemps by remember { mutableIntStateOf(0) }
    var actualRandomCode by remember { mutableIntStateOf(userPasswordState.randomCode) }

    LaunchedEffect(recoverPasswordViewModel.recoveryMessage) {
        recoverPasswordViewModel.recoveryMessage.collect {
            recoveryMessage = it
        }
    }

    LaunchedEffect(userPasswordState.randomCode) {
        if (userPasswordState.randomCode != -2) {
            actualRandomCode = userPasswordState.randomCode
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
            text = if (emailSent) stringResource(R.string.get_code_title) else stringResource(R.string.recoverpassword_title),
            style = MaterialTheme.typography.displayLarge,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 28.dp)
        )

        if (emailSent) {
            OutlinedTextField(

                value = code,
                textStyle = TextStyle(
                    color = Color.Black
                ),
                onValueChange = { code = it },
                label = { Text(text = stringResource(id = R.string.code_label)) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            )
        } else {
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
        }


        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {

                // Verifica las condiciones y ajusta el título y el onClick
                if (emailSent) {
                    println("El code escrito es: " + code + " y el codigo real es " + actualRandomCode.toString())
                    println(code::class)
                    println(actualRandomCode::class)
                    println(date)

                    // Obtener la fecha actual
                    val currentDate = Instant.now().atZone(ZoneId.of("GMT")).toLocalDateTime()

                    // Parsear la fecha de expiración
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
                    val expirationDateTime = LocalDateTime.parse(date, formatter)

                    // Comparar las fechas
                    val hasExpired = currentDate.isAfter(expirationDateTime)

                    println("expiro: " + hasExpired)
                    println("fecha actual: " + currentDate + " fecha codigo: " + expirationDateTime)
                    if (code == actualRandomCode.toString()) {
                        if (hasExpired) {
                            println("SI EXPIRO")
                            emailSent = false
                            expirationDialog = true

                        } else {
                            // Aqui programa Alejandro Marroquin Almeida el cambio de contraseña
                            navigateNewPassword()

                            println("NO EXPIRO")
                        }
                        println("FUNCIONNAAAAAAAA")
                    } else {
                        failedAttemps++
                        if (failedAttemps <= 3) {
                            if (failedAttemps == 3) {
                                //CODIGOOO
                                lastAttemptDialog = true
                            }
                            wrongCode = true
                        } else {
                            //AQUI SE PROGRAMA LA SUSPENSION DEL USUARIO
                            val userEmail = LockoutModel(email)
                            val response = lockoutUserViewModel.lockoutUser(userEmail)
                            println(response)
                            suspendDialog = true
                            emailSent = false
                            failedAttemps = 0
                            code = ""
                        }
                    }

                } else {
                    val userEmail = RecoverPasswordModel(email)
                    val response = recoverPasswordViewModel.recoverPassword(userEmail)
                    coroutineScope.launch {
                        DataStoreSingleton.saveUserData(
                            UserDataResponseExpress(
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                email
                            )
                        )
                    }
                    Log.d("response register: ", response.toString())
                }

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

        if (expirationDialog) {
            CustomAlertDialog(
                title = stringResource(id = R.string.expirationTitle),
                message = stringResource(id = R.string.expirationMessage),
                confirmText = stringResource(id = R.string.expirationConfirm),
                onDismiss = {
                    recoverPasswordViewModel.resetState()
                    expirationDialog = false }
            )
        }

        if (lastAttemptDialog) {
            CustomAlertDialog(
                title = stringResource(id = R.string.titleAlert),
                message = stringResource(id = R.string.messageAlert),
                confirmText = stringResource(id = R.string.confirmAlert),
                onDismiss = { lastAttemptDialog = false }
            )
        }

        if (wrongCode) {
            CustomAlertDialog(
                title = stringResource(id = R.string.wrongCodeTitle),
                message = stringResource(id = R.string.wrongCodeMessage),
                confirmText = stringResource(id = R.string.expirationConfirm),
                onDismiss = { wrongCode = false }
            )
        }

        if (userPasswordState.isEmailSentSuccessfully && userPasswordState.isRequestSuccessful) {
            CustomAlertDialog(
                title = stringResource(id = R.string.recover_email_sent_title),
                message = stringResource(id = R.string.recover_email_sent),
                onDismiss = { recoverPasswordViewModel.resetState() }
            )
            emailSent = true
            println(userPasswordState.randomCode)
            actualRandomCode = userPasswordState.randomCode
            date = userPasswordState.expirationDate.toString()
            println(actualRandomCode)
            println(userPasswordState.randomCode)
        }

        if (!userPasswordState.isEmailSentSuccessfully && userPasswordState.isEmailValid && userPasswordState.isRequestSuccessful) {
            CustomAlertDialog(
                title = stringResource(id = R.string.error),
                message = stringResource(id = R.string.code_exists),
                onDismiss = { recoverPasswordViewModel.resetState() }
            )
            emailSent = true
            actualRandomCode = userPasswordState.randomCode
            date = userPasswordState.expirationDate.toString()
//            navigateNewPassword()
        }

        if (!userPasswordState.isEmailValid && userPasswordState.isRequestSuccessful) {
            CustomAlertDialog(
                title = stringResource(id = R.string.error),
                message = stringResource(id = R.string.recover_invalid_email),
                onDismiss = { recoverPasswordViewModel.resetState() }
            )
        }

        if (userPasswordState.userSuspended || suspendDialog) {
            CustomAlertDialog(
                title = stringResource(id = R.string.lockoutUserTitle),
                message = stringResource(id = R.string.lockoutUserMessage),
                onDismiss = {
                    recoverPasswordViewModel.resetState()
                    suspendDialog = false
                }
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
