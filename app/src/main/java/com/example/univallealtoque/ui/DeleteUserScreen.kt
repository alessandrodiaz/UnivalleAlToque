package com.example.univallealtoque.ui

import CustomAlertDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.univallealtoque.R
import androidx.navigation.NavController
import com.example.univallealtoque.UnivalleAlToqueScreen
import com.example.univallealtoque.data.DataStoreSingleton
import com.example.univallealtoque.model.DeleteAccountModel
import com.example.univallealtoque.model.SendCodeDeleteAccountModel
import com.example.univallealtoque.model.UserDataResponseExpress
import com.example.univallealtoque.user_account.DeleteAccountConfirmViewModel
import com.example.univallealtoque.user_account.SendCodeDeleteAccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteUserScreen(
    navController: NavController,
    modifier: Modifier,
    onSignOut: () -> Unit,
) {
    var code by rememberSaveable { mutableStateOf("") }

    val sendCodeDeleteAccountModel: DeleteAccountConfirmViewModel = viewModel()
    val sendCodeState by sendCodeDeleteAccountModel.state.collectAsState()

    val userDataFlow = DataStoreSingleton.getUserData().collectAsState(initial = null)
    val userCode = userDataFlow.value?.user_id?.toString() ?: "null"

    var navigateHome = { navController.navigate(UnivalleAlToqueScreen.HomePage.name) }



    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        item {
            Spacer(modifier = Modifier.height(100.dp))

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
                    value = code,
                    textStyle = TextStyle(
                        color = Color.Black
                    ),
                    onValueChange = { code = it },
                    label = { Text(text = stringResource(id = R.string.code)) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
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
                            Log.d("DATOS A ENVIAR", userCode + code)
                            val data = DeleteAccountModel(userCode, code)
                            val response = sendCodeDeleteAccountModel.deleteAccount(data)

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

                if (sendCodeState.isAccountDeleted && sendCodeState.isRequestSuccessful) {
                    CustomAlertDialog(
                        title = stringResource(id = R.string.delete_account_title),
                        message = stringResource(id = R.string.delete_account_done),
                        onDismiss = { sendCodeDeleteAccountModel.resetState() }
                    )

                    navigateHome()
                    onSignOut()
                }

                if (!sendCodeState.isCodeValid && sendCodeState.isRequestSuccessful) {
                    CustomAlertDialog(
                        title = stringResource(id = R.string.error),
                        message = stringResource(id = R.string.delete_account_invalid_code),
                        onDismiss = {
                            sendCodeDeleteAccountModel.resetState()
                        }
                    )

                }
            }
        }
    }
}