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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CreateNewActivityScreen(
    navController: NavController,
    modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){

            Spacer(modifier = Modifier.height(70.dp))

            Text(
                text = stringResource(id = R.string.add_new_activity),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(30.dp))

            LazyColumn(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    Button(
                        onClick = {navController.navigate(UnivalleAlToqueScreen.CreateNewActivityEvent.name)},
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .padding(16.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(
                            text = stringResource(id = R.string.createEvents),
                            color = Color.White
                        )
                    }
                    Button(
                        onClick = {navController.navigate(UnivalleAlToqueScreen.CreateNewActivityGroup.name)},
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .padding(16.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(
                            text = stringResource(id = R.string.createSemilleros),
                            color = Color.White
                        )
                    }
                }
            }
    }
}



@Composable
fun ActivityImageSelectionScreen(
//    viewModelExpress: LoginViewModelExpress,
//    capturedImageUri: (imageName: String) -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let { saveActivityImageToInternalStorage(context, it) }
        }
    )

    Button(onClick = { launcher.launch("image/*") }) {
        Text(text = stringResource(id = R.string.choose_activitiy),)
    }
}


fun saveActivityImageToInternalStorage(
//    viewModelExpress: LoginViewModelExpress,
    context: Context,
    uri: Uri,
//    capturedImageUri: (imageName: String) -> Unit
) {
    val inputStream = context.contentResolver.openInputStream(uri)
    val imageName = "image.jpg"
    val outputStream = context.openFileOutput("image.jpg", Context.MODE_PRIVATE)
    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
//            capturedImageUri(imageName)
            println(uri)
            ActivityStorageUtil.uploadToStorageActivity(
//                viewModelExpress = viewModelExpress,
                uri = uri,
                context = context,
                type = "image"
            )
//            println("url of uploaded image: ${viewModelExpress.stateLoginExpress}")
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DemoSearchableDropdown(
    myHint: String,
    myArrayOptions: Array<String>,
    setVariableTo: (varName: String) -> Unit,
    myComponentWithDP: Int
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    Box(
        modifier = Modifier
            .width(myComponentWithDP.dp) // Ajusta el ancho según tu preferencia
            .padding(top = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.background(Color.White)
        ) {
            val filteredOptions =
                myArrayOptions.filter { it.contains(selectedText, ignoreCase = true) }

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
                readOnly = true,
                label = { Text(text = myHint) },
                textStyle = TextStyle(color = if (selectedText.isNotEmpty()) Color.Black else Color.Gray),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
            )

            if (filteredOptions.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { /* No ocultar el menú al cambiar el texto */ },
                    modifier = Modifier.background(Color.White)
                ) {
                    myArrayOptions.forEach { item ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = item,
                                    color = Color.Black
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




