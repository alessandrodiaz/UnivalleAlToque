package com.example.univallealtoque.ui

import android.content.Context
import android.net.Uri
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.univallealtoque.R
import com.example.univallealtoque.UnivalleAlToqueScreen
import com.example.univallealtoque.data.AppDataStoreSingleton
import com.example.univallealtoque.data.DataStoreSingleton
import com.example.univallealtoque.sign_in_express.LoginViewModelExpress
import com.example.univallealtoque.util.ActivityStorageUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class CreateNewActivityEventRequest(
    var nameOfActivity: String? = null,
    var typeOfActivity: String? = null,
    var description: String? = null,
    var numberOfSlotsAvailable: Int? = null,
    var slots: Int? = null,
    var mondayStart: String? = null, // "00:00",
    var mondayEnd: String? = null, // "00:00",
    var tuesdayStart: String? = null, // "00:00",
    var tuesdayEnd: String? = null, // "00:00",
    var wednesdayStart: String? = null, // "00:00",
    var wednesdayEnd: String? = null, // "00:00",
    var thursdayStart: String? = null, // "00:00",
    var thursdayEnd: String? = null, // "00:00",
    var fridayStart: String? = null, // "00:00",
    var fridayEnd: String? = null, // "00:00",
    var saturdayStart: String? = null, // "00:00",
    var saturdayEnd: String? = null, // "00:00",
    var placeOfActivity: String? = null
)


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CreateNewActivityEventScreen(
    userModelExpress: LoginViewModelExpress,
    navController: NavController,
    modifier: Modifier
) {

    //var profilePhotoToShow = userDataFlow.value?.profile_photo ?: "null"

    val userDataFlow = DataStoreSingleton.getUserData().collectAsState(initial = null)
    val userModelExpressState by userModelExpress.loginOrUpdateResponseFromServer.collectAsState()

    val myNewActivityRequest = CreateNewActivityEventRequest()

    var nameOfActivityInputText by remember { mutableStateOf("") }
    var descriptionOfActivityInputText by remember { mutableStateOf("") }
    var placeOfActivityInputText by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    var hasFocusNameOfActivity by remember { mutableStateOf(false) }
    var hasFocusDescription by remember { mutableStateOf(false) }
    var hasFocusPlace by remember { mutableStateOf(false) }
    var hasFocusCupos by remember { mutableStateOf(false) }
    var myColor by remember { mutableStateOf(Color.Blue) }

    var value by remember {
        mutableStateOf("")
    }

    var isClicked by remember { mutableStateOf(false) }
    val borderWidth = 4.dp
    var showDialogChoosePhoto by remember { mutableStateOf(false) }

    val context = LocalContext.current

    var hours = Array(1){ ""} +  Array(24) { i -> "%02d:00".format(i) }
    var weekDays = arrayOf(stringResource(id = R.string.monday), stringResource(id = R.string.tuesday), stringResource(id = R.string.wednesday), stringResource(id = R.string.thursday), stringResource(id = R.string.friday), stringResource(id = R.string.saturday))
    var typeActivity = arrayOf(stringResource(id = R.string.semillero_title), stringResource(id = R.string.event_title))

    val appDataFlow = AppDataStoreSingleton.getAppData().collectAsState(initial = null)


//    var numberVacancies = arrayOf("3", "5", "10", "20", "30", "1000")
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Spacer(modifier = Modifier.height(70.dp))

            Text(
                text = stringResource(id = R.string.add_new_activity),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(30.dp))


            // NOMBRE DE LA ACTIVIDAD
            Text(
                text = stringResource(id = R.string.name_of_activity),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayMedium,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
            )

            BasicTextField(
                value = nameOfActivityInputText,
                onValueChange = { nameOfActivityInputText = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = if (hasFocusNameOfActivity) Color.Red else Color.Gray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .height(40.dp)
                    .padding(start = 16.dp, top = 8.dp, end = 8.dp)
                    .background(Color.White)
                    .onFocusEvent { focusState ->
                        hasFocusNameOfActivity = focusState.isFocused
                    }
            )


            // TIPO
            Text(
                text = stringResource(id = R.string.type_of_activity),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayMedium,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 0.dp)
            )
            DemoSearchableDropdown(
                myHint = stringResource(id = R.string.type_activity),
                myArrayOptions = typeActivity,
                { param: String -> myNewActivityRequest.typeOfActivity = param },
                myComponentWithDP = 210,
            )


            // DESCRIPCION
            Text(
                text = stringResource(id = R.string.description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayMedium,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
            )

            BasicTextField(
                value = descriptionOfActivityInputText,
                onValueChange = { descriptionOfActivityInputText = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = if (hasFocusDescription) Color.Red else Color.Gray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .height(100.dp)
                    .padding(start = 16.dp, top = 8.dp, end = 8.dp)
                    .background(Color.White)
                    .onFocusEvent { focusState ->
                        hasFocusDescription = focusState.isFocused
                    }
            )

            // LUGAR
            Text(
                text = stringResource(id = R.string.place),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayMedium,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
            )


            BasicTextField(
                value = placeOfActivityInputText,
                onValueChange = { placeOfActivityInputText = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = if (hasFocusPlace) Color.Red else Color.Gray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .height(40.dp)
                    .padding(start = 16.dp, top = 8.dp, end = 8.dp)
                    .background(Color.White)
                    .onFocusEvent { focusState ->
                        hasFocusPlace = focusState.isFocused
                    }
            )


            // NUMERO DE CUPOS
            Text(
                text = stringResource(id = R.string.number_of_slots),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayMedium,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
            )

            val maxDigits = 4

            BasicTextField(
                value = value.take(maxDigits),
                onValueChange = {
                    val newValue = it.filter { char -> char.isDigit() }
                    value = newValue
                    if (newValue.isNotEmpty()) {
                        myNewActivityRequest.numberOfSlotsAvailable = newValue.toInt()
                        myNewActivityRequest.slots = newValue.toInt()
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }),
                modifier = Modifier
                    .width(150.dp)
                    .border(
                        width = 1.dp,
                        color = if (hasFocusCupos) Color.Red else Color.Gray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .height(40.dp)
                    .padding(start = 16.dp, top = 8.dp, end = 8.dp)
                    .background(Color.White)
                    .onFocusEvent { focusState ->
                        hasFocusCupos = focusState.isFocused
                    }
            )




            // HORARIO
            Text(
                text = stringResource(id = R.string.schedule),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayMedium,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 0.dp)

            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                weekDays.forEach { item ->
                    Row {
                        Text(
                            text = item,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.Black,
                            modifier = Modifier
                                .width(80.dp)
                                .padding(top = 16.dp, end = 10.dp, bottom = 0.dp)
                                .align(Alignment.CenterVertically)
                        )
                        if (item == stringResource(id = R.string.monday)) {
                            DemoSearchableDropdown(
                                myHint = stringResource(id = R.string.start),
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.mondayStart = param },
                                myComponentWithDP = 130
                            )
                            DemoSearchableDropdown(
                                myHint = stringResource(id = R.string.end),
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.mondayEnd = param },
                                myComponentWithDP = 130
                            )
                        }
                        if (item == stringResource(id = R.string.tuesday)) {
                            DemoSearchableDropdown(
                                myHint = stringResource(id = R.string.start),
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.tuesdayStart = param },
                                myComponentWithDP = 130
                            )
                            DemoSearchableDropdown(
                                myHint = stringResource(id = R.string.end),
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.tuesdayEnd = param },
                                myComponentWithDP = 130
                            )
                        }
                        if (item == stringResource(id = R.string.wednesday)) {
                            DemoSearchableDropdown(
                                myHint = stringResource(id = R.string.start),
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.wednesdayStart = param },
                                myComponentWithDP = 130
                            )
                            DemoSearchableDropdown(
                                myHint = stringResource(id = R.string.end),
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.wednesdayEnd = param },
                                myComponentWithDP = 130
                            )
                        }
                        if (item == stringResource(id = R.string.thursday)) {
                            DemoSearchableDropdown(
                                myHint = stringResource(id = R.string.start),
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.thursdayStart = param },
                                myComponentWithDP = 130
                            )
                            DemoSearchableDropdown(
                                myHint = stringResource(id = R.string.end),
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.thursdayEnd = param },
                                myComponentWithDP = 130
                            )
                        }
                        if (item == stringResource(id = R.string.friday)) {
                            DemoSearchableDropdown(
                                myHint = stringResource(id = R.string.start),
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.fridayStart = param },
                                myComponentWithDP = 130
                            )
                            DemoSearchableDropdown(
                                myHint = stringResource(id = R.string.end),
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.fridayEnd = param },
                                myComponentWithDP = 130
                            )
                        }
                        if (item == stringResource(id = R.string.saturday)) {
                            DemoSearchableDropdown(
                                myHint = stringResource(id = R.string.start),
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.saturdayStart = param },
                                myComponentWithDP = 130
                            )
                            DemoSearchableDropdown(
                                myHint = stringResource(id = R.string.end),
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.saturdayEnd = param },
                                myComponentWithDP = 130
                            )
                        }
                    }
                }
            }

            // AGREGAR FOTO
            Text(
                text = stringResource(R.string.create_activity_photo),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayMedium,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 0.dp)

            )

            Spacer(modifier = Modifier.height(16.dp))

            ActivityImageSelectionScreen()

            Spacer(modifier = Modifier.height(16.dp))

            var navigateMyGroups = { navController.navigate(UnivalleAlToqueScreen.MyGroups.name) }
            // BOTÓN AGREGAR ACTIVIDAD
            Button(
                onClick = {

                    val userId = userDataFlow.value?.user_id
                    val imageUrl = appDataFlow.value?.toString() ?: "no_image_provided"
                    var imageValid = false
                    Log.d("imageUrl", imageUrl)

                    if (Patterns.WEB_URL.matcher(imageUrl).matches()) {
                        imageValid = true
                        // Si imageUrl contiene una URL válid
                        Log.d("URL Check", "La imageUrl contiene una URL válida")
                    } else {
                        imageValid = false
                        // Si imageUrl no contiene una URL válida
                        Log.d("URL Check", "La imageUrl no contiene una URL válida")
                    }


                    myNewActivityRequest.nameOfActivity = nameOfActivityInputText
                    myNewActivityRequest.description = descriptionOfActivityInputText
                    myNewActivityRequest.placeOfActivity = placeOfActivityInputText

                    if (myNewActivityRequest.typeOfActivity != null) {
                        val nameOfActivity = myNewActivityRequest.nameOfActivity
                        val descriptionOfActivity = myNewActivityRequest.description
                        val placeOfActivity = myNewActivityRequest.placeOfActivity


                        if (
                            myNewActivityRequest.slots == null ||
                            myNewActivityRequest.numberOfSlotsAvailable == null
                        ) {
                            Toast.makeText(
                                context,
                                "Error: Debes ingresar el numero de cupos primero",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (imageValid == false) {
                            Toast.makeText(
                                context,
                                "Error: Debes agregar una imagen para tu actividad",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (myNewActivityRequest.nameOfActivity == null) {
                            Toast.makeText(
                                context,
                                "Error: Debes proporcionar un nombre para la actividad",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else if (nameOfActivity != null && nameOfActivity.length <= 4) {
                            Toast.makeText(
                                context,
                                "Error: Debes proporcionar un nombre para la actividad más largo",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (descriptionOfActivity!= null && descriptionOfActivity.length <= 4){
                            Toast.makeText(
                                context,
                                "Error: Debes proporcionar una descripción para tu actividad",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (placeOfActivity != null && placeOfActivity.length <= 4){
                            Toast.makeText(
                                context,
                                "Error: Debes proporcionar un lugar para la actividad",
                                Toast.LENGTH_SHORT
                            ).show()
                        }



                        else if (userId != null) {
                            println("IMAGEN URL" + imageUrl)
                            userModelExpress.createNewActivity(
                                creatorId = userId,
                                nameOfActivity = myNewActivityRequest.nameOfActivity,
                                typeOfActivity = myNewActivityRequest.typeOfActivity,
                                description = myNewActivityRequest.description,
                                numberOfSlotsAvailable = myNewActivityRequest.numberOfSlotsAvailable,
                                slots = myNewActivityRequest.slots,
                                mondayStart = myNewActivityRequest.mondayStart,
                                mondayEnd = myNewActivityRequest.mondayEnd,
                                tuesdayStart = myNewActivityRequest.tuesdayStart,
                                tuesdayEnd = myNewActivityRequest.tuesdayEnd,
                                wednesdayStart = myNewActivityRequest.wednesdayStart,
                                wednesdayEnd = myNewActivityRequest.wednesdayEnd,
                                thursdayStart = myNewActivityRequest.thursdayStart,
                                thursdayEnd = myNewActivityRequest.thursdayEnd,
                                fridayStart = myNewActivityRequest.fridayStart,
                                fridayEnd = myNewActivityRequest.fridayEnd,
                                saturdayStart = myNewActivityRequest.saturdayStart,
                                saturdayEnd = myNewActivityRequest.saturdayEnd,
                                photo = imageUrl,
                                place = myNewActivityRequest.placeOfActivity
                            )
                            Toast.makeText(
                                context,
                                "Actividad creada con éxito!",
                                Toast.LENGTH_SHORT
                            ).show()

                            navigateMyGroups()
                            CoroutineScope(Dispatchers.Main).launch {
                                AppDataStoreSingleton.saveAppData("")
                            }

                        } else {
                            Toast.makeText(
                                context,
                                "Error: Debes iniciar sesion primero (userId not found)",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        Toast.makeText(
                            context,
                            "Error: Debes de elegir el tipo de actividad primero",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp), // Esquinas redondeadas
            ) {
                Text(
                    text = "AGREGAR ACTIVIDAD",
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(52.dp))

        }
    }
}

