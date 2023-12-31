package com.example.univallealtoque.ui

import CustomAlertDialog
import android.util.Log
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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.univallealtoque.UnivalleAlToqueScreen
import com.example.univallealtoque.data.DataStoreSingleton
import com.example.univallealtoque.model.ChangePasswordModel
import com.example.univallealtoque.model.RegisterModel
import com.example.univallealtoque.model.SendCodeDeleteAccountModel
import com.example.univallealtoque.user_account.ChangePasswordViewModel
import com.example.univallealtoque.user_account.SendCodeDeleteAccountViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    modifier: Modifier,
) {

    //CHANGE PASSWORD
    var old_password by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var repeat_password by rememberSaveable { mutableStateOf("") }

    val changePasswordModel: ChangePasswordViewModel = viewModel()
    val changePasswordState by changePasswordModel.state.collectAsState()

    val dialogState = remember { mutableStateOf<ChangePasswordDialogState?>(null) }

    //DELETE ACCOUNT
    var delete_account_password by rememberSaveable { mutableStateOf("") }

    val sendCodeDeleteAccountModel: SendCodeDeleteAccountViewModel = viewModel()
    val sendCodeState by sendCodeDeleteAccountModel.state.collectAsState()

    //USER DATA FROM DATASTORE
    val userDataFlow = DataStoreSingleton.getUserData().collectAsState(initial = null)
    val userCode = userDataFlow.value?.user_id?.toString() ?: "null"

    var navigateDeleteUserScreen = { navController.navigate(UnivalleAlToqueScreen.DeleteUser.name) }
    var navigateProfile = { navController.navigate(UnivalleAlToqueScreen.Profile.name) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        item {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = stringResource(R.string.settings_title),
                style = MaterialTheme.typography.displayLarge,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 28.dp)
            )


            /**
             * CHANGE PASSWORD
             */
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(R.string.settings_change_password_title),
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 28.dp, start = 16.dp)
                )


                //OLD PASSWORD FIELD
                OutlinedTextField(
                    value = old_password,
                    textStyle = TextStyle(
                        color = Color.Black
                    ),
                    onValueChange = { old_password = it },
                    label = { Text(text = stringResource(id = R.string.settings_current_password)) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),

                    )

                //NEW PASSWORD FIELD
                OutlinedTextField(
                    value = password,
                    textStyle = TextStyle(
                        color = Color.Black
                    ),
                    onValueChange = { password = it },
                    label = { Text(text = stringResource(id = R.string.settings_new_password)) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),

                    )

                //REPEAT NEW PASSWORD FIELD
                OutlinedTextField(
                    value = repeat_password,
                    textStyle = TextStyle(
                        color = Color.Black
                    ),
                    onValueChange = { repeat_password = it },
                    label = { Text(text = stringResource(id = R.string.settings_repeat_new_password)) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),

                    )

                Spacer(modifier = Modifier.height(16.dp))
                //BUTTON CHANGE PASSWORD

                val context = LocalContext.current
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

                            Log.d(
                                "BOTON ONCLICK",
                                userCode + " " + old_password + " " + password + " " + repeat_password
                            )

                            val containsLetter = password.any { it.isLetter() }
                            val containsDigit = password.any { it.isDigit() }
                            val containsSpecialChar = password.any { !it.isLetterOrDigit() }

                            val validationState: ChangePasswordDialogState? = when {
                                //PASSWORD LENGTH
                                password.length < 8 -> ChangePasswordDialogState.InvalidPassword(
                                    context.getString(
                                        R.string.register_invalid_password
                                    )
                                )

                                //CONTAINS A LETTER
                                !containsLetter -> ChangePasswordDialogState.InvalidPassword(
                                    context.getString(R.string.register_invalid_password_criteria_letter)
                                )

                                //CONTAINS A NUMBER
                                !containsDigit -> ChangePasswordDialogState.InvalidPassword(
                                    context.getString(R.string.register_invalid_password_criteria_number)
                                )

                                //CONTAINS A SYMBOL
                                !containsSpecialChar -> ChangePasswordDialogState.InvalidPassword(
                                    context.getString(R.string.register_invalid_password_criteria_symbol)
                                )


                                password != repeat_password -> ChangePasswordDialogState.InvalidPassword(
                                    context.getString(R.string.register_password_mismatch)
                                )
                                else -> null
                            }

                            if (validationState == null) {
                                Log.d(
                                    "DATOS A ENVIAR",
                                    userCode + " " + old_password + " " + password + " " + repeat_password
                                )
                                val data = ChangePasswordModel(userCode, old_password, password)
                                val response = changePasswordModel.changePassword(data)

                                Log.d("RESPUESTA: ", response.toString())

                            } else {
                                dialogState.value = validationState
                            }

                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxHeight(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.settings_change_password_title),
                            style = MaterialTheme.typography.displaySmall,
                            color = Color.White
                        )
                    }
                }

                dialogState.value?.let { state ->
                    when (state) {
                        is ChangePasswordDialogState.InvalidPassword -> {
                            CustomAlertDialog(
                                title = "",
                                message = state.message,
                                onDismiss = { dialogState.value = null })
                        }

                        ChangePasswordDialogState.InvalidInput -> {
                            CustomAlertDialog(
                                title = "",
                                message = stringResource(id = R.string.register_invalid_data),
                                onDismiss = { dialogState.value = null })
                        }
                    }


                }

                if (changePasswordState.isPasswordUpdated && changePasswordState.isRequestSuccessful) {
                    CustomAlertDialog(
                        title = stringResource(id = R.string.settings_password_updated_title),
                        message = stringResource(id = R.string.settings_password_updated),
                        onDismiss = { changePasswordModel.resetState() }
                    )

                    navigateProfile()
                }

                if (!changePasswordState.isOldPasswordValid && changePasswordState.isRequestSuccessful) {
                    CustomAlertDialog(
                        title = stringResource(id = R.string.settings_password_invalid_title),
                        message = stringResource(id = R.string.settings_password_invalid),
                        onDismiss = { changePasswordModel.resetState() }
                    )
                }
            }


            Spacer(modifier = Modifier.height(24.dp))

            /**
             * DELETE ACCOUNT
             */
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(R.string.settings_delete_account_title),
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 28.dp, start = 16.dp)
                )

                Text(
                    text = stringResource(R.string.settings_delete_account_description),
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 28.dp, start = 16.dp, end = 16.dp)
                )

                OutlinedTextField(
                    value = delete_account_password,
                    textStyle = TextStyle(
                        color = Color.Black
                    ),
                    onValueChange = { delete_account_password = it },
                    label = { Text(text = stringResource(id = R.string.register_password)) },
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
                            Log.d("DATOS A ENVIAR", userCode + delete_account_password)
                            val userPassword =
                                SendCodeDeleteAccountModel(userCode, delete_account_password)
                            val response =
                                sendCodeDeleteAccountModel.sendCodeDeleteAccount(userPassword)

                            Log.d("RESPUESTA: ", response.toString())
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxHeight(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.settings_delete_account_button),
                            style = MaterialTheme.typography.displaySmall,
                            color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(100.dp))

                if (sendCodeState.isEmailSentSuccessfully && sendCodeState.isRequestSuccessful) {
                    CustomAlertDialog(
                        title = stringResource(id = R.string.recover_email_sent_title),
                        message = stringResource(id = R.string.recover_email_sent),
                        onDismiss = { sendCodeDeleteAccountModel.resetState() }
                    )

                    navigateDeleteUserScreen()
                }

                if (!sendCodeState.isPasswordValid && sendCodeState.isRequestSuccessful) {
                    CustomAlertDialog(
                        title = stringResource(id = R.string.error),
                        message = stringResource(id = R.string.delete_account_invalid_password),
                        onDismiss = { sendCodeDeleteAccountModel.resetState() }
                    )
                }
            }
        }
    }
}

sealed class ChangePasswordDialogState {
    object InvalidInput : ChangePasswordDialogState()
    data class InvalidPassword(val message: String) : ChangePasswordDialogState()
}