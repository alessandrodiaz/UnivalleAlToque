package com.example.univallealtoque.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.univallealtoque.sign_in_express.LoginViewModelExpress
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.univallealtoque.R
import com.example.univallealtoque.data.DataStoreSingleton
import com.example.univallealtoque.model.UserDataResponseExpress

data class CreateNewActivityRequest(
    var nameOfActivity: String? = null,
    var typeOfActivity: String? = null,
    var description: String? = null,
    var numberOfSlotsAvailable:Int? = null,
    var slots:Int? = null,
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
)



@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CreateNewActivityScreen(
    userModelExpress: LoginViewModelExpress,
    navController: NavController,
    modifier: Modifier
) {

    //var profilePhotoToShow = userDataFlow.value?.profile_photo ?: "null"

    val userDataFlow = DataStoreSingleton.getUserData().collectAsState(initial = null)
    val userModelExpressState by userModelExpress.loginOrUpdateResponseFromServer.collectAsState()

    val myNewActivityRequest = CreateNewActivityRequest()

    var nameOfActivityInputText by remember { mutableStateOf("") }
    var descriptionOfActivityInputText by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    var hasFocusNameOfActivity by remember { mutableStateOf(false) }
    var hasFocusDescription by remember { mutableStateOf(false) }
    var myColor by remember { mutableStateOf(Color.Blue) }

    var value by remember {
        mutableStateOf("")
    }

    var isClicked by remember { mutableStateOf(false) }
    val borderWidth = 4.dp
    var showDialogChoosePhoto by remember { mutableStateOf(false) }

    val context = LocalContext.current

    var hours = Array(24) { i -> "%02d:00".format(i) }
    var weekDays = arrayOf("Lunes","Martes","Miercoles","Jueves","Viernes","Sabado")
    var typeActivity = arrayOf("Semillero","Evento")
    var numberVacancies = arrayOf("10","20","30","40","50","1000")
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Spacer(modifier = Modifier.height(52.dp))
            Text(
                text = "Agregar nueva actividad",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge,
                color = Color.Black
            )
            Text(
                text = "Nombre de la actividad",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
            )

            BasicTextField(
                value = nameOfActivityInputText,
                onValueChange = { nameOfActivityInputText = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()}),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = if (hasFocusNameOfActivity) Color.Red else Color.Black,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .height(40.dp)
                    .padding(start = 16.dp, top = 8.dp, end = 8.dp)
                    .background(Color.White)
                    .onFocusEvent { focusState ->
                        hasFocusNameOfActivity = focusState.isFocused
                    }
            )

            Text(
                text = "Tipo:",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 0.dp)
            )
            DemoSearchableDropdown(
                myHint = "Tipo Actividad",
                myArrayOptions = typeActivity,
                { param: String -> myNewActivityRequest.typeOfActivity = param },
                myComponentWithDP = 300, )
            Text(
                text = "Descripción",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
            )

            BasicTextField(
                value = descriptionOfActivityInputText,
                onValueChange = { descriptionOfActivityInputText = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()}),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = if (hasFocusDescription) Color.Red else Color.Black,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .height(120.dp)
                    .padding(start = 16.dp, top = 8.dp, end = 8.dp)
                    .background(Color.White)
                    .onFocusEvent { focusState ->
                        hasFocusDescription = focusState.isFocused
                    }
            )

            Text(
                text = "Número de Cupos",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
            )

            DemoSearchableDropdown(
                myHint = "Cupos",
                myArrayOptions = numberVacancies,
                { param: String ->
                    myNewActivityRequest.numberOfSlotsAvailable = param.toInt();
                    myNewActivityRequest.slots = param.toInt();
                },
                myComponentWithDP = 300)

            Text(
                text = "Horario:",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 0.dp)
            )

            Column{
                weekDays.forEach { item ->
                    Row {
                        Text(
                            text = item,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.Black,
                            modifier = Modifier
                                .width(76.dp)
                                .padding(top = 16.dp,end = 12.dp, bottom = 0.dp)
                        )
                        if (item == "Lunes"){
                            DemoSearchableDropdown(
                                myHint = "Inicio",
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.mondayStart = param },
                                myComponentWithDP = 150)
                            DemoSearchableDropdown(
                                myHint = "Fin",
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.mondayEnd = param },
                                myComponentWithDP = 150)
                        }
                        if (item == "Martes"){
                            DemoSearchableDropdown(
                                myHint = "Inicio",
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.tuesdayStart = param },
                                myComponentWithDP = 150)
                            DemoSearchableDropdown(
                                myHint = "Fin",
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.tuesdayEnd = param },
                                myComponentWithDP = 150)
                        }
                        if (item == "Miercoles"){
                            DemoSearchableDropdown(
                                myHint = "Inicio",
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.wednesdayStart = param },
                                myComponentWithDP = 150)
                            DemoSearchableDropdown(
                                myHint = "Fin",
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.wednesdayEnd = param },
                                myComponentWithDP = 150)
                        }
                        if (item == "Jueves"){
                            DemoSearchableDropdown(
                                myHint = "Inicio",
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.thursdayStart = param },
                                myComponentWithDP = 150)
                            DemoSearchableDropdown(
                                myHint = "Fin",
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.thursdayEnd = param },
                                myComponentWithDP = 150)
                        }
                        if (item == "Viernes"){
                            DemoSearchableDropdown(
                                myHint = "Inicio",
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.fridayStart = param },
                                myComponentWithDP = 150)
                            DemoSearchableDropdown(
                                myHint = "Fin",
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.fridayEnd = param },
                                myComponentWithDP = 150)
                        }
                        if (item == "Sabado"){
                            DemoSearchableDropdown(
                                myHint = "Inicio",
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.saturdayStart = param },
                                myComponentWithDP = 150)
                            DemoSearchableDropdown(
                                myHint = "Fin",
                                myArrayOptions = hours,
                                { param: String -> myNewActivityRequest.saturdayEnd = param },
                                myComponentWithDP = 150)
                        }
                    }
                }
            }


            //if (showDialogChoosePhoto) {
            //    showNewImageOptions(
            //        true,
            //        userModelExpress,
            //        { param: String -> profilePhotoToShow = param },
            //        { showDialogChoosePhoto = false })
            //}


            Button(
                onClick = {
                    Log.d("d","sdnfsdnfsd")
                    val userId = userDataFlow.value?.user_id
                    myNewActivityRequest.nameOfActivity = nameOfActivityInputText
                    myNewActivityRequest.description = descriptionOfActivityInputText

                    if ( myNewActivityRequest.typeOfActivity != null){

                        if (userId != null) {
                            userModelExpress.createNewActivity(
                                creatorId = userId,
                                nameOfActivity = myNewActivityRequest.nameOfActivity,
                                typeOfActivity= myNewActivityRequest.typeOfActivity,
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
                            )
                            Toast.makeText(context, "Actividad creada con éxito!", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(context, "Error: Debes iniciar sesion primero (userId not found)", Toast.LENGTH_SHORT).show()
                        }

                    }else{
                        Toast.makeText(context, "Error: Debes de elegir el tipo de actividad primero", Toast.LENGTH_SHORT).show()
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


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DemoSearchableDropdown(
    myHint: String,
    myArrayOptions: Array<String>,
    setVariableTo: (varName: String) -> Unit,
    myComponentWithDP: Int) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    Box(
        modifier = Modifier
            .width(myComponentWithDP.dp) // Ajusta el ancho según tu preferencia
            .padding(top= 16.dp, end= 16.dp, bottom = 16.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            val filteredOptions = myArrayOptions.filter { it.contains(selectedText, ignoreCase = true) }

            TextField(
                value = selectedText,
                onValueChange = {
                    selectedText = it
                    setVariableTo(it)
                    // Cambiar el color del texto a negro después de seleccionar una opción
                    //if (filteredOptions.contains(it)) {
                    //    selectedText = it
                    //}
                },
                label = { Text(text = myHint) },
                textStyle = TextStyle(color = if (selectedText.isNotEmpty()) Color.Black else Color.Gray),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()}),
                modifier = Modifier
                    .menuAnchor()
            )

            if (filteredOptions.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { /* No ocultar el menú al cambiar el texto */ }
                ) {
                    filteredOptions.forEach { item ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = item,
                                    color = if (item == selectedText) Color.Black else Color.Unspecified
                                )
                            },
                            onClick = {
                                selectedText = item
                                setVariableTo(item)
                                expanded = false
                                // Cambiar el color del texto a negro después de seleccionar una opción
                                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }
}

