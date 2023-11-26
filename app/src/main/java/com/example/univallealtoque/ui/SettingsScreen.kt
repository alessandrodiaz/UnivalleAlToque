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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.univallealtoque.UnivalleAlToqueScreen
import com.example.univallealtoque.model.RegisterModel
import com.example.univallealtoque.sign_in_express.RegisterViewModel
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    modifier: Modifier,
) {

//    var name by remember { mutableStateOf("") }
//    var last_name by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }

    //CHANGE PASSWORD
    var old_password by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var repeat_password by rememberSaveable { mutableStateOf("") }

    //DELETE ACCOUNT
    var delete_account_password by rememberSaveable { mutableStateOf("") }

//    var navigateLogin = { navController.navigate(UnivalleAlToqueScreen.Login.name) }
//    var navigateTermsAndConditios = {navController.navigate(UnivalleAlToqueScreen.TermsAndConditions.name)}
//    var navigatePrivacyPolicy = {navController.navigate(UnivalleAlToqueScreen.PrivacyPolicy.name)}
//    var checkboxState by remember { mutableStateOf(false) }
//
//    val dialogState = remember { mutableStateOf<RegisterDialogState?>(null) }
//
//    val viewModel: RegisterViewModel = viewModel()
//    val registerState by viewModel.state.collectAsState()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
//            .fillMaxWidth()
//            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        item{
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
            Column( modifier = Modifier.fillMaxSize(),){
                Text(
                    text = stringResource(R.string.settings_change_password_title),
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 28.dp, start=16.dp)
                )


                //OLD PASSWORD FIELD
                OutlinedTextField(
                    value = old_password,
                    textStyle = TextStyle(
                        color = Color.Black
                    ),
                    onValueChange = { password = it },
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
                    value = password,
                    textStyle = TextStyle(
                        color = Color.Black
                    ),
                    onValueChange = { password = it },
                    label = { Text(text = stringResource(id = R.string.settings_repeat_new_password)) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),

                    )

                Spacer(modifier = Modifier.height(16.dp))
                //BUTTON CHANGE PASSWORD
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
                        onClick = {},
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
            }


            Spacer(modifier = Modifier.height(24.dp))

            /**
             * DELETE ACCOUNT
             */
            Column( modifier = Modifier.fillMaxSize(),) {
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
                        onClick = {},
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
            }
        }
    }
}