package com.example.univallealtoque.ui

//import com.example.univallealtoque.sign_in_google.UserData


import CustomAlertDialog
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color.rgb
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.univallealtoque.BuildConfig
import com.example.univallealtoque.R
import com.example.univallealtoque.UnivalleAlToqueScreen
import com.example.univallealtoque.data.DataStoreSingleton
import com.example.univallealtoque.sign_in_express.LoginViewModelExpress
import com.example.univallealtoque.sign_in_google.UserDataGoogle
import com.example.univallealtoque.util.StorageUtil
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userDataGoogle: UserDataGoogle?,
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
    var showDialogChoosePhoto by remember { mutableStateOf(false) }
    var showDialogChooseAvatar by remember { mutableStateOf(false) }

    //Obtener datos del usuario del DataStore
    val userDataFlow = DataStoreSingleton.getUserData().collectAsState(initial = null)

    val nameToShow = userDataFlow.value?.name ?: "null"
    val lastNameToShow = userDataFlow.value?.last_name ?: "null"
    var profilePhotoToShow = userDataFlow.value?.profile_photo ?: "null"
    val emailToShow = userDataFlow.value?.email ?: "null"
    val phoneToShow = userDataFlow.value?.phone ?: "null"
    val programToShow = userDataFlow.value?.program ?: "null"

    var emailOfUser by remember { mutableStateOf(emailToShow) }
    var phoneOfUser by remember { mutableStateOf(phoneToShow) }
    var programOfUser by remember { mutableStateOf(programToShow) }
    var showAlertDialog by remember { mutableStateOf(false) }

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
    var isClicked by remember { mutableStateOf(false) }
    val borderWidth = 4.dp
    LazyColumn(
        modifier = modifier
    ) {
        item {
            CircleShape()

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                //PROFILE PICTURE
                if (profilePhotoToShow != "null") {
                    var displayAvatarInt = 0;
                    when (profilePhotoToShow) {
                        "avatar1" -> {
                            displayAvatarInt = R.drawable.avatar1
                        }

                        "avatar2" -> {
                            displayAvatarInt = R.drawable.avatar2
                        }

                        "avatar3" -> {
                            displayAvatarInt = R.drawable.avatar3
                        }

                        "avatar4" -> {
                            displayAvatarInt = R.drawable.avatar4
                        }

                        "avatar5" -> {
                            displayAvatarInt = R.drawable.avatar5
                        }

                        "avatar6" -> {
                            displayAvatarInt = R.drawable.avatar6
                        }

                        else -> {
                        }
                    }
                    if (displayAvatarInt != 0) {
                        Image(
                            painter = painterResource(id = displayAvatarInt),
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
                                .clickable {
                                    // Toggle the value on each click
                                    isClicked = !isClicked
                                    // Print to console
                                    println("Image Clicked: $isClicked")
                                    showDialogChoosePhoto = true

                                }
                        )
                    } else {//Tiene una url a una imagen de internet
                        AsyncImage(
                            //bitmap = BitmapFactory.decodeFile(File(LocalContext.current.filesDir, profilePhotoToShow).absolutePath).asImageBitmap(),
                            model = profilePhotoToShow,
                            contentDescription = stringResource(id = R.string.profile_picture_description),
                            modifier = Modifier
                                .size(150.dp)
                                .border(
                                    BorderStroke(borderWidth, rainbowColorsBrush),
                                    CircleShape
                                )
                                .padding(borderWidth)
                                .clip(CircleShape)
                                .clickable {
                                    // Toggle the value on each click
                                    isClicked = !isClicked
                                    // Print to console
                                    println("Image Clicked: $isClicked")
                                    showDialogChoosePhoto = true

                                },
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                } else {//Sin imagen de perfil
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
                            .clickable {
                                // Toggle the value on each click
                                isClicked = !isClicked
                                // Print to console
                                println("Image Clicked: $isClicked")
                                showDialogChoosePhoto = true

                            }
                    )
                }

                //USERNAME
                if (nameToShow != "null" && lastNameToShow != "null") {
                    Text(
                        text = "$nameToShow",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.White
                    )
                    Text(
                        text = "$lastNameToShow",
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

                if (showDialogChoosePhoto) {
                    showNewImageOptions(
                        true,
                        userModelExpress,
                        { param: String -> profilePhotoToShow = param },
                        { showDialogChooseAvatar = true },
                        { showDialogChoosePhoto = false })
                }

                if (showDialogChooseAvatar) {
                    showNewAvatarOptions(
                        true,
                        userModelExpress,
                        { param: String -> profilePhotoToShow = param },
                        { showDialogChooseAvatar = false })
                }

                phoneOfUser?.let { infoPart("Celular", it, { showDialogChangePhone = true }) }


                if (showDialogChangePhone) {
                    phoneOfUser?.let {
                        ShowMessageDialog(
                            titleOfDialog = stringResource(id = R.string.change_phone),
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
                            titleOfDialog = stringResource(id = R.string.change_degree),
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
                        title = stringResource(id = R.string.dates_changed),
                        message = stringResource(id = R.string.peticion_succesfull),
                        onDismiss = { booleanResponseSuccessFromUpdateBasicData = false })
                }

                Spacer(modifier = Modifier.height(35.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                ) {
                    IconButton(onClick =
                    {
                        navController.navigate(UnivalleAlToqueScreen.Settings.name)
                    }) {

                        Image(
                            painter = painterResource(id = R.drawable.settings),
                            contentDescription = stringResource(id = R.string.settings),
                            modifier = Modifier
                                .scale(1.2f)
                                .clip(CircleShape)
                        )
                    }
                    IconButton(onClick =
                    {
                        showAlertDialog = true
                    }) {

                        Image(
                            painter = painterResource(id = R.drawable.logout),
                            contentDescription = stringResource(id = R.string.logout),
                            modifier = Modifier
                                .scale(1.2f)
                                .clip(CircleShape)
                        )
                    }
                }

                if (showAlertDialog) {
                    AlertDialog(
                        containerColor = Color.White,
                        onDismissRequest = { /* No realizar ninguna acción al hacer clic fuera del cuadro de diálogo */ },
                        title = {
                            Text(
                                text = stringResource(id = R.string.titleLogout),
                                style = MaterialTheme.typography.displayMedium,
                                color = Color.Black
                            )
                        },
                        text = {
                            Text(
                                text = stringResource(id = R.string.messageLogout),
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Black
                            )
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    // Lógica para salir de la sesión
                                    onSignOut()
                                    userModelExpress.resetLoginStateExpress()
                                    navController.navigate(UnivalleAlToqueScreen.HomePage.name)
                                    showAlertDialog = false
                                }
                            ) {
                                Text(stringResource(id = R.string.confirmLogout))
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    // Lógica para cancelar la salida
                                    showAlertDialog = false
                                }
                            ) {
                                Text(stringResource(id = R.string.cancelLogout))
                            }
                        }
                    )
                }


                Spacer(modifier = Modifier.height(95.dp))


            }
        }
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
                    Text(stringResource(id = R.string.acept))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onClose()
                    }
                ) {
                    Text(stringResource(id = R.string.cancel))
                }
            },
            modifier = Modifier
                .defaultMinSize(300.dp)
                .border(0.dp, Color.Transparent, RoundedCornerShape(16.dp))


        )
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun showNewImageOptions(
    show: Boolean,
    viewModelExpress: LoginViewModelExpress,
    setProfilePhotoToShow: (imageName: String) -> Unit,
    setShowNewAvatarOptions: () -> Unit,
    onClose: () -> Unit
) {

    if (show) {
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = {
                onClose()
            },
            title = {
                Text(stringResource(id = R.string.new_profile_picture), color = Color.Black)
            },
            text = {
                LazyColumn(
                    modifier = Modifier
                ) {
                    item {
                        Button(
                            onClick = {
                                viewModelExpress.updateBasicData(newProfilePhoto = "null")
                                setProfilePhotoToShow("null");
                                onClose()
                            }) {
                            Text(text = stringResource(id = R.string.without_profile_picture))
                        }
                        imageCaptureFromCamera(viewModelExpress, setProfilePhotoToShow)
                        ImageSelectionScreen(viewModelExpress, setProfilePhotoToShow)
                        Button(onClick = { setShowNewAvatarOptions();onClose() }) {
                            Text(text = stringResource(id = R.string.choose_avatar))
                        }
                    }
                }


            },
            confirmButton = {
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onClose()
                    }
                ) {
                    Text(stringResource(id = R.string.acept))
                }
            },
            modifier = Modifier
                .defaultMinSize(1700.dp)
                .border(0.dp, Color.Transparent, RoundedCornerShape(16.dp))


        )
    }
}


@Composable
fun imageCaptureFromCamera(
    viewModelExpress: LoginViewModelExpress,
    setProfilePhotoToShow: (imageName: String) -> Unit
) {

    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file)


    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }


    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            capturedImageUri = uri
        }


    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }




    Button(onClick = {
        val permissionCheckResult =
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)

        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            cameraLauncher.launch(uri)
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }) {
        Text(text = stringResource(id = R.string.take_picture))
    }


    if (capturedImageUri.path?.isNotEmpty() == true) {
        println("Imagen tomada: $capturedImageUri")
        saveImageToInternalStorage(
            viewModelExpress,
            context,
            capturedImageUri,
            setProfilePhotoToShow
        )
    }
}

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyy_MM_dd_HH:mm:ss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        filesDir
    )
    println(filesDir)

    return image
}

@Composable
fun ImageSelectionScreen(
    viewModelExpress: LoginViewModelExpress,
    capturedImageUri: (imageName: String) -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let { saveImageToInternalStorage(viewModelExpress, context, it, capturedImageUri) }
        }
    )

    Button(onClick = { launcher.launch("image/*") }) {
        Text(stringResource(id = R.string.choose_picture))
    }
}

//Used for the function of taking pictures with cameraX and for retrieving images from gallery
//It will do the job of uploading the picture to google drive
//It will do the job of updating the viewModel of the user to link with google drive
//maybe I will need another function to store an image from google drive into internal store so
//the rest of the code works without any editing
//beacuse all the images wiil be store in google drive, it's besto edit the code of showing images,
//to work viewing the images from google drive only.
//the viewModel I think will have the link of the image serve on google drive.

fun saveImageToInternalStorage(
    viewModelExpress: LoginViewModelExpress,
    context: Context,
    uri: Uri,
    capturedImageUri: (imageName: String) -> Unit
) {
    val inputStream = context.contentResolver.openInputStream(uri)
    val imageName = "image.jpg"
    val outputStream = context.openFileOutput("image.jpg", Context.MODE_PRIVATE)
    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
            capturedImageUri(imageName)
            println(uri)
            StorageUtil.uploadToStorage(
                viewModelExpress = viewModelExpress,
                uri = uri,
                context = context,
                type = "image"
            )
            println("url of uploaded image: ${viewModelExpress.stateLoginExpress}")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun showNewAvatarOptions(
    show: Boolean,
    viewModelExpress: LoginViewModelExpress,
    setProfilePhotoToShow: (imageName: String) -> Unit,
    onClose: () -> Unit
) {

    var selectedImage by remember { mutableStateOf("null") }

    var isClicked by remember { mutableStateOf(false) }
    var borderWidth1 by remember { mutableStateOf(0.dp) }
    var borderWidth2 by remember { mutableStateOf(0.dp) }
    var borderWidth3 by remember { mutableStateOf(0.dp) }
    var borderWidth4 by remember { mutableStateOf(0.dp) }
    var borderWidth5 by remember { mutableStateOf(0.dp) }
    var borderWidth6 by remember { mutableStateOf(0.dp) }

    if (show) {
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = {
                onClose()
            },
            title = {
                Text(stringResource(id = R.string.choose_avatar_final), color = Color.Black)
            },
            text = {
                LazyColumn(
                    modifier = Modifier
                ) {
                    item {
                        var rainbowColorsBrush = remember {
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
                        Row {
                            Spacer(modifier = Modifier.width(16.dp))
                            Image(
                                painter = painterResource(id = R.drawable.avatar1),
                                contentDescription = stringResource(id = R.string.login_title),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(60.dp)
                                    .border(
                                        BorderStroke(borderWidth1, rainbowColorsBrush),
                                        CircleShape
                                    )
                                    .padding(borderWidth1)
                                    .clip(CircleShape)
                                    .clickable {
                                        selectedImage = "avatar1"
                                        borderWidth1 = 4.dp
                                        borderWidth2 = 0.dp
                                        borderWidth3 = 0.dp
                                        borderWidth4 = 0.dp
                                        borderWidth5 = 0.dp
                                        borderWidth6 = 0.dp
                                        // Print to console
                                        println("Image Clicked: avatar1")
                                    }
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Image(
                                painter = painterResource(id = R.drawable.avatar2),
                                contentDescription = stringResource(id = R.string.login_title),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(60.dp)
                                    .border(
                                        BorderStroke(borderWidth2, rainbowColorsBrush),
                                        CircleShape
                                    )
                                    .padding(borderWidth2)
                                    .clip(CircleShape)
                                    .clickable {
                                        selectedImage = "avatar2"
                                        borderWidth2 = 4.dp
                                        borderWidth1 = 0.dp
                                        borderWidth3 = 0.dp
                                        borderWidth4 = 0.dp
                                        borderWidth5 = 0.dp
                                        borderWidth6 = 0.dp
                                        // Print to console
                                        println("Image Clicked: avatar2")
                                    }
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Image(
                                painter = painterResource(id = R.drawable.avatar3),
                                contentDescription = stringResource(id = R.string.login_title),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(60.dp)
                                    .border(
                                        BorderStroke(borderWidth3, rainbowColorsBrush),
                                        CircleShape
                                    )
                                    .padding(borderWidth3)
                                    .clip(CircleShape)
                                    .clickable {
                                        selectedImage = "avatar3"
                                        borderWidth3 = 4.dp
                                        borderWidth2 = 0.dp
                                        borderWidth1 = 0.dp
                                        borderWidth4 = 0.dp
                                        borderWidth5 = 0.dp
                                        borderWidth6 = 0.dp
                                        // Print to console
                                        println("Image Clicked: avatar3")
                                    }
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row {
                            Spacer(modifier = Modifier.width(16.dp))
                            Image(
                                painter = painterResource(id = R.drawable.avatar4),
                                contentDescription = stringResource(id = R.string.login_title),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(60.dp)
                                    .border(
                                        BorderStroke(borderWidth4, rainbowColorsBrush),
                                        CircleShape
                                    )
                                    .padding(borderWidth4)
                                    .clip(CircleShape)
                                    .clickable {
                                        selectedImage = "avatar4"
                                        borderWidth4 = 4.dp
                                        borderWidth2 = 0.dp
                                        borderWidth3 = 0.dp
                                        borderWidth1 = 0.dp
                                        borderWidth5 = 0.dp
                                        borderWidth6 = 0.dp
                                        // Print to console
                                        println("Image Clicked: avatar4")
                                    }
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Image(
                                painter = painterResource(id = R.drawable.avatar5),
                                contentDescription = stringResource(id = R.string.login_title),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(60.dp)
                                    .border(
                                        BorderStroke(borderWidth5, rainbowColorsBrush),
                                        CircleShape
                                    )
                                    .padding(borderWidth5)
                                    .clip(CircleShape)
                                    .clickable {
                                        selectedImage = "avatar5"
                                        borderWidth5 = 4.dp
                                        borderWidth2 = 0.dp
                                        borderWidth3 = 0.dp
                                        borderWidth4 = 0.dp
                                        borderWidth1 = 0.dp
                                        borderWidth6 = 0.dp
                                        // Print to console
                                        println("Image Clicked: avatar5")
                                    }
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Image(
                                painter = painterResource(id = R.drawable.avatar6),
                                contentDescription = stringResource(id = R.string.login_title),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(60.dp)
                                    .border(
                                        BorderStroke(borderWidth6, rainbowColorsBrush),
                                        CircleShape
                                    )
                                    .padding(borderWidth6)
                                    .clip(CircleShape)
                                    .clickable {
                                        selectedImage = "avatar6"
                                        borderWidth6 = 4.dp
                                        borderWidth2 = 0.dp
                                        borderWidth3 = 0.dp
                                        borderWidth4 = 0.dp
                                        borderWidth5 = 0.dp
                                        borderWidth1 = 0.dp
                                        // Print to console
                                        println("Image Clicked: avatar6")
                                    }
                            )
                        }


                    }
                }


            },
            confirmButton = {
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        println("SELECTED IMAGE: "+selectedImage)
                        setProfilePhotoToShow(selectedImage)
                        if (selectedImage != "null") {
                            viewModelExpress.updateBasicData(newProfilePhoto = selectedImage)
                        }
                        onClose()
                    }
                ) {
                    Text(stringResource(id = R.string.acept))
                }
            },
            modifier = Modifier
                .defaultMinSize(1700.dp)
                .border(0.dp, Color.Transparent, RoundedCornerShape(16.dp))


        )
    }
}