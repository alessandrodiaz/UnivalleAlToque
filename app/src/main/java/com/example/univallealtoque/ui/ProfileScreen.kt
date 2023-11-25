package com.example.univallealtoque.ui

import CustomAlertDialog
import android.graphics.Color.rgb
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.univallealtoque.R
import com.example.univallealtoque.UnivalleAlToqueScreen
import com.example.univallealtoque.sign_in_express.LoginViewModelExpress
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import com.example.univallealtoque.data.DataStoreSingleton
import com.example.univallealtoque.model.UserDataExpress
import com.example.univallealtoque.sign_in_google.UserData
//import com.example.univallealtoque.sign_in_google.UserData
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userData: UserData?,
//    userDataExpress: UserDataExpress?,
    onSignOut: () -> Unit,
    userModelExpress: LoginViewModelExpress,
    navController: NavController,
    modifier: Modifier
) {
    val userModelExpressState by userModelExpress.loginOrUpdateResponseFromServer.collectAsState()
    var showDialogChangeEmail by remember { mutableStateOf(false) }
    var showDialogChangePhone by remember { mutableStateOf(false) }
    var showDialogChangeProgram by remember { mutableStateOf(false) }

    //Obtener datos del usuario del DataStore
    val userDataFlow = DataStoreSingleton.getUserData().collectAsState(initial = null)

    val nameToShow = userDataFlow.value?.name ?: "null"
    val lastNameToShow = userDataFlow.value?.last_name ?: "null"
    val profilePhotoToShow = userDataFlow.value?.profile_photo ?: "null"
    val emailToShow = userDataFlow.value?.email ?: "null"
    val phoneToShow = userDataFlow.value?.phone ?: "null"
    val programToShow = userDataFlow.value?.program ?: "null"

    var emailOfUser by remember { mutableStateOf(emailToShow) }
    var phoneOfUser by remember { mutableStateOf(phoneToShow) }
    var programOfUser by remember { mutableStateOf(programToShow) }

    LaunchedEffect(userDataFlow.value) {
        emailOfUser = userDataFlow.value?.email ?: "null"
        phoneOfUser = userDataFlow.value?.phone ?: "null"
        programOfUser = userDataFlow.value?.program ?: "null"
    }

    var showMessageAfterUpdate by remember { mutableStateOf(false) }

    var booleanResponseSuccessFromUpdateBasicData by remember { mutableStateOf(false) }

    val rainbowColorsBrush = remember {
        Brush.sweepGradient(
            listOf(
                Color(0xFF9575CD),
                Color(0xFFBA68C8),
                Color(0xFFE57373),
                Color(0xFFFFB74D),
                Color(0xFFFFF176),
                Color(0xFFAED581),
                Color(0xFF4DD0E1),
                Color(0xFF9575CD)
            )
        )
    }
    val interactionSource = remember { MutableInteractionSource() }

    val borderWidth = 4.dp
    LazyColumn(
        modifier = modifier
    ) {
        item{
            CircleShape()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //PROFILE PICTURE
            if (profilePhotoToShow != "null") {
                AsyncImage(
                    model = "$profilePhotoToShow",
                    contentDescription = stringResource(id = R.string.profile_picture_description),
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                Image(
                    painter = painterResource(id = R.drawable.user6),
                    contentDescription = stringResource(id = R.string.login_title),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(160.dp)
                        .border(
                            BorderStroke(borderWidth, rainbowColorsBrush),
                            CircleShape
                        )
                        .padding(borderWidth)
                        .clip(CircleShape)
                )
            }

            //USERNAME
            if (nameToShow != "null" && lastNameToShow != "null") {
                Text(
                    text = "$nameToShow $lastNameToShow",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(25.dp))

            } else {
                userModelExpressState.userData?.name?.let {
                    Text(
                        text = it.uppercase(Locale.ROOT),
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                    )
                }
                userModelExpressState.userData?.last_name?.let {
                    Text(
                        text = it.uppercase(Locale.ROOT),
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }


            Spacer(modifier = Modifier.height(100.dp))
            IconButton(onClick = {}) {
                Icon(
                    Icons.Rounded.ThumbUp,
                    contentDescription = "Thumb Up",
                    tint = Color.Red,
                    modifier = Modifier
                        .rotate(-20f)
                        .size(40.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "0",
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .background(Color(rgb(234, 31, 1)), shape = CircleShape)
                    .circleLayout()
                    .padding(0.dp)

            )
            emailOfUser?.let { infoPart("Correo", it) { showDialogChangeEmail = true } }
//
//            if (showDialogChangeEmail) {
//                emailOfUser?.let {
//                    ShowMessageDialog(
//                        titleOfDialog = "Cambiar Correo",
//                        hint = it,
//                        show = showDialogChangeEmail,
//                        viewModelExpress = userModelExpress,
//                        setUserUpdate = { param: String -> emailOfUser = param },
//                        setSuccessOrFailure = { param: Boolean ->
//                            booleanResponseSuccessFromUpdateBasicData = param
//                        },
//                        onClose = { showDialogChangeEmail = false })
//                }
//            }

            phoneOfUser?.let { infoPart("Celular", it, { showDialogChangePhone = true }) }

            if (showDialogChangePhone) {
                phoneOfUser?.let {
                    ShowMessageDialog(
                        titleOfDialog = "Cambiar Celular",
                        hint = it,
                        show = showDialogChangePhone,
                        viewModelExpress = userModelExpress,
                        setUserUpdate = { param: String -> phoneOfUser = param },
                        setSuccessOrFailure = { param: Boolean ->
                            booleanResponseSuccessFromUpdateBasicData = param
                        },
                        onClose = { showDialogChangePhone = false })
                }
            }

            programOfUser?.let { infoPart("Carrera", it, { showDialogChangeProgram = true }) }

            if (showDialogChangeProgram) {
                programOfUser?.let {
                    ShowMessageDialog(
                        titleOfDialog = "Cambiar Programa",
                        hint = it,
                        show = showDialogChangeProgram,
                        viewModelExpress = userModelExpress,
                        setUserUpdate = { param: String -> programOfUser = param },
                        setSuccessOrFailure = { param: Boolean ->
                            booleanResponseSuccessFromUpdateBasicData = param
                        },
                        onClose = { showDialogChangeProgram = false })
                }
            }


            if (booleanResponseSuccessFromUpdateBasicData) {
                CustomAlertDialog(
                    title = "Dato actualizado",
                    message = "Petición exitosa",
                    onDismiss = { booleanResponseSuccessFromUpdateBasicData = false })
            }

            Spacer(modifier = Modifier.height(25.dp))

            IconButton(onClick =
            {
                onSignOut()
                userModelExpress.resetLoginStateExpress()
                navController.navigate(UnivalleAlToqueScreen.HomePage.name)
            }) {

                Image(
                    painter = painterResource(id = R.drawable.logout),
                    contentDescription = stringResource(id = R.string.logout),
//                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .scale(1.2f)
//                        .size(3000.dp)
//                        .padding(borderWidth)
                        .clip(CircleShape)
                )
            }


        }}
    }
}


@Composable
fun CircleShape() {
    Canvas(modifier = Modifier.size(0.dp),
        onDraw = {
            val size = 550.dp.toPx()
            scale(scaleX = 1.1f, scaleY = 1.3f) {
                drawCircle(
                    color = Color(rgb(234, 31, 1)),
                    radius = size / 2f,
                    center = Offset(
                        x = size / 2 - 80,
                        y = -40.dp.toPx(),
                    )
                )
            }
        })
}

fun Modifier.circleLayout() =
    layout { measurable, constraints ->
        // Measure the composable
        val placeable = measurable.measure(constraints)

        //get the current max dimension to assign width=height
        val currentHeight = placeable.height
        val currentWidth = placeable.width
        val newDiameter = maxOf(currentHeight, currentWidth)

        //assign the dimension and the center position
        layout(newDiameter, newDiameter) {
            // Where the composable gets placed
            placeable.placeRelative((newDiameter - currentWidth) / 2, -10)
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun infoPart(infoName: String, infoValue: String, func: () -> Unit) {
    var text by remember { mutableStateOf("") }
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        infoName,
        style = TextStyle(
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        ),
        modifier = Modifier
            .padding(start = 24.dp)
            .fillMaxWidth()
    )
    OutlinedTextField(
        value = infoValue.uppercase(Locale.ROOT),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        ),

        onValueChange = { text = text },
        enabled = false,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .clickable { func() }
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .height(height = 52.dp)
            .border(
                width = 2.dp,
                color = Color.Red,
                shape = RoundedCornerShape(12.dp)
            )
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ShowMessageDialog(
    titleOfDialog: String,
    hint: String,
    show: Boolean,
    viewModelExpress: LoginViewModelExpress,
    setUserUpdate: (userUpdate: String) -> Unit,
    setSuccessOrFailure: (success: Boolean) -> Unit,
    onClose: () -> Unit
) {
    var titleOfDialog by remember { mutableStateOf(titleOfDialog) }
    var myHint by remember { mutableStateOf(hint) }
    val keyboardController = LocalSoftwareKeyboardController.current
    if (show) {
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = {
                onClose()
            },
            title = {
                Text(titleOfDialog, color = Color.Black)
            },
            text = {
                TextField(
                    value = myHint,
                    onValueChange = {
                        myHint = it

                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Puedes hacer algo con el texto ingresado aquí
                        onClose()////////////////////////////////////////////////////////////////////COLOCAR EL UPDATE A LA BD AQUI ABAJO
                        var getSuccess = false;
                        when (titleOfDialog) {

                            "Cambiar Celular" -> {
                                viewModelExpress.updateBasicData(newPhone = myHint)
                                //getSuccess = viewModelExpress.stateLoginExpress.value.updateSuccessful == true
                            }

                            "Cambiar Programa" -> {
                                viewModelExpress.updateBasicData(newProgram = myHint)
                                //getSuccess = viewModelExpress.stateLoginExpress.value.updateSuccessful == true
                            }

                            else -> {}
                        }
                        // no se puede hacer un if porque el viewmodelexpress no alcanza a actualizarse
                        //getSuccess = viewModelExpress.stateLoginExpress.value.updateSuccessful ?: false

                        setSuccessOrFailure(true)
                        setUserUpdate(myHint)///ESTO PASA SI EL UPDATE HA SIDO EXITOSO


                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onClose()
                    }
                ) {
                    Text("Cancelar")
                }
            },
            modifier = Modifier
                .defaultMinSize(300.dp)
                .border(0.dp, Color.Transparent, RoundedCornerShape(16.dp))


        )
    }
}
