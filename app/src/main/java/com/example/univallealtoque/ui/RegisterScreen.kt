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
fun RegisterScreen(
    navController: NavController,
    modifier: Modifier,
) {

    var name by remember { mutableStateOf("") }
    var last_name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var repeat_password by rememberSaveable { mutableStateOf("") }
    var navigateLogin = { navController.navigate(UnivalleAlToqueScreen.Login.name) }
    var navigateTermsAndConditios = {navController.navigate(UnivalleAlToqueScreen.TermsAndConditions.name)}
    var navigatePrivacyPolicy = {navController.navigate(UnivalleAlToqueScreen.PrivacyPolicy.name)}
    var checkboxState by remember { mutableStateOf(false) }

    val dialogState = remember { mutableStateOf<RegisterDialogState?>(null) }

    val viewModel: RegisterViewModel = viewModel()
    val registerState by viewModel.state.collectAsState()

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
                modifier = Modifier.padding(bottom = 28.dp)
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
                value = last_name,
                textStyle = TextStyle(
                    color = Color.Black
                ),
                onValueChange = { last_name = it },
                label = { Text(text = stringResource(id = R.string.register_last_name)) },
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

            Checkbox(value = stringResource(id = R.string.register_conditions),
                onTextSelected = {
                    navigateTermsAndConditios()
                },isChecked = checkboxState,
                onCheckedChange = { newCheckedState ->
                    checkboxState = newCheckedState
                },
                onTextSelected2 = {
                    navigatePrivacyPolicy()
                })

            Spacer(modifier = Modifier.height(28.dp))


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

                        val emailValidationState = isValidEmail(email)

                        if (emailValidationState != null) {
                            dialogState.value = emailValidationState
                            return@Button
                        }

                        val validationState: RegisterDialogState? = when {
                            name.length < 3 -> RegisterDialogState.InvalidName(context.getString(R.string.register_invalid_name))
                            password.length < 6 -> RegisterDialogState.InvalidPassword(
                                context.getString(
                                    R.string.register_invalid_password
                                )
                            )

                            password != repeat_password -> RegisterDialogState.InvalidPassword(
                                context.getString(R.string.register_password_mismatch)
                            )

                            !checkboxState -> RegisterDialogState.UncheckedConditions(
                                context.getString(R.string.register_unchecked_terms)
                            )

                            else -> null
                        }

                        if (validationState == null) {
                            val registerData = RegisterModel(name, last_name, email, password)

                            val response = viewModel.registerUser(registerData)
                            Log.d("response register: ", response.toString())

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
                        text = stringResource(R.string.register_title),
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.White
                    )
                }
            }

            dialogState.value?.let { state ->
                when (state) {
                    is RegisterDialogState.InvalidName -> {
                        CustomAlertDialog(
                            title = "",
                            message = state.message,
                            onDismiss = { dialogState.value = null })
                    }

                    is RegisterDialogState.InvalidEmail -> {
                        CustomAlertDialog(
                            title = "",
                            message = state.message,
                            onDismiss = { dialogState.value = null })
                    }

                    is RegisterDialogState.InvalidPassword -> {
                        CustomAlertDialog(
                            title = "",
                            message = state.message,
                            onDismiss = { dialogState.value = null })
                    }

                    is RegisterDialogState.UncheckedConditions -> {
                        CustomAlertDialog(
                            title = "",
                            message = state.message,
                            onDismiss = { dialogState.value = null })
                    }

                    RegisterDialogState.InvalidInput -> {
                        CustomAlertDialog(
                            title = "",
                            message = stringResource(id = R.string.register_invalid_data),
                            onDismiss = { dialogState.value = null })
                    }
                }


            }

            if (registerState.isRegisterSuccessful) {
                var showDialog by remember { mutableStateOf(true) }

                if (showDialog) {
                    LaunchedEffect(true) {
                        delay(4000) // Espera 4 segundos antes de cerrar el diálogo
                        showDialog = false
                        navController.navigate(UnivalleAlToqueScreen.Login.name)
                        dialogState.value = null
                        viewModel.resetState()
                    }
                }

                if (showDialog) {
                    Dialog(

                        onDismissRequest = {}
                    ) {
                        Box() {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .padding(16.dp),
                                shape = RoundedCornerShape(16.dp),
                            ) {
                                Text(
                                    text = stringResource(id = R.string.register_text_success),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .wrapContentSize(Alignment.Center),
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }
            }

            if (registerState.registerError) {
                var showDialog by remember { mutableStateOf(true) }

                if (showDialog) {
                    LaunchedEffect(true) {
                        delay(4000)
                        showDialog = false
                    }
                }

                if (showDialog) {
                    Dialog(
                        onDismissRequest = {}
                    ) {
                        Box() {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .padding(16.dp),
                                shape = RoundedCornerShape(16.dp),
                            ) {
                                Text(
                                    text = stringResource(id = R.string.register_text_error),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .wrapContentSize(Alignment.Center),
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }
            }

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


sealed class RegisterDialogState {
    object InvalidInput : RegisterDialogState()
    data class InvalidName(val message: String) : RegisterDialogState()
    data class InvalidEmail(val message: String) : RegisterDialogState()
    data class InvalidPassword(val message: String) : RegisterDialogState()
    data class UncheckedConditions(val message: String) : RegisterDialogState()
}


fun isValidEmail(email: String): RegisterDialogState? {
    return if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        null // Si el correo es válido, devuelve null
    } else {
        RegisterDialogState.InvalidEmail("El correo no es válido")
    }
}

@Composable
fun NormalTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 25.dp),
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
    color = colorResource(id = R.color.colorText)
    )
}

@Composable
fun Checkbox(
    value: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onTextSelected: (String) -> Unit,
    onTextSelected2: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        androidx.compose.material3.Checkbox(
            checked = isChecked,
            onCheckedChange = { newCheckedState ->
                onCheckedChange(newCheckedState)
            }
        )
        ClickableTextComponent(value = value, onTextSelected, onTextSelected2)
    }
}


@Composable
fun ClickableTextComponent(value: String, onTextSelected: (String) -> Unit, onTextSelected2: (String) -> Unit){

    val initialText = "Al continuar aceptas nuestra "
    val privacyPolicyText = "Política de privacidad"
    val yText = " y "
    val termsAndConditions = "Terminos de uso"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Color(0xFF92A3FD))) {
            pushStringAnnotation(tag = privacyPolicyText, annotation = privacyPolicyText)
            append(privacyPolicyText)
        }
        append(yText)
        withStyle(style = SpanStyle(color = Color(0xFF92A3FD))) {
            pushStringAnnotation(tag = termsAndConditions, annotation = termsAndConditions)
            append(termsAndConditions)
        }
    }

    ClickableText(text = annotatedString, onClick = {
        offset -> annotatedString.getStringAnnotations(offset,offset)
        .firstOrNull()?.also {span->
            Log.d("ClickableTextComponent","{$span}")

            if(span.item == termsAndConditions ){
                onTextSelected(span.item)
            }
            else {
                onTextSelected2(span.item)
            }

        }
    })
}