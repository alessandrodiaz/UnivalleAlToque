package com.example.univallealtoque.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.univallealtoque.R
import com.example.univallealtoque.UnivalleAlToqueScreen
import com.example.univallealtoque.activities.EnrolledActivitiesViewModel
import com.example.univallealtoque.data.DataStoreSingleton
import com.example.univallealtoque.model.EnrolledActivitiesModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyGroupsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val enrolledActivitiesModel: EnrolledActivitiesViewModel = viewModel()
    val enrolledActivitiesState by enrolledActivitiesModel.state.collectAsState()
    val activitiesList by enrolledActivitiesModel.activities.collectAsState()

    //USER DATA FROM DATASTORE
    val userDataFlow = DataStoreSingleton.getUserData().collectAsState(initial = null)
    val userCode = userDataFlow.value?.user_id?.toString() ?: "null"

    //GET ACTIVITIES
    // Utilizando LaunchedEffect para ejecutar la lógica una vez al ingresar a la pantalla
    if (userCode != null && userCode != "null"){
        LaunchedEffect(key1 = enrolledActivitiesState.isRequestSuccessful) {
            if (!enrolledActivitiesState.isListObtained && !enrolledActivitiesState.isRequestSuccessful) {
                Log.d("USER CODE " , userCode)
                val data = EnrolledActivitiesModel(userCode)
                val response = enrolledActivitiesModel.enrolledActivities(data)
                Log.d("LISTA ACTIVIDADES: ", response.toString())
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxHeight()
                .align(Alignment.TopStart)
        ) {
            item {
                Spacer(modifier = Modifier.height(80.dp))
                Text(
                    text = stringResource(id = R.string.my_groups_title),
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 18.dp, bottom = 18.dp)
                )

                if(activitiesList.isEmpty()){
                    Text(
                        text = stringResource(id = R.string.my_groups_no_activities),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 18.dp, bottom = 18.dp, top = 20.dp)
                    )
                } else {
                    activitiesList.forEach { activity ->
                        ActivityCard(
                            imagen = activity.group_photo ?: activity.event_photo ?: "Sin imagen", // Reemplaza con tu lógica para obtener la imagen
                            titulo = activity.group_name ?: activity.event_name ?: "Sin título",
                            descripcion = activity.group_description?.limitTextLength(100) ?: activity.event_description?.limitTextLength(100) ?: "Sin descripción"
                        )
                    }
                }

                Spacer(modifier = modifier.height(60.dp))
            }
        }

        FloatingActionButton(
            onClick = {
                navController.navigate(UnivalleAlToqueScreen.CreateNewActivity.name)
            },
            contentColor = MaterialTheme.colorScheme.background,
            containerColor = Color(0xFFEA1F01),
            modifier = Modifier
                .padding(bottom = 90.dp, end = 40.dp)
                .size(56.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                modifier = Modifier
                    .size(56.dp)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
fun ActivityCard(
    imagen: String?,
    titulo: String,
    descripcion: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = Modifier
                .size(126.dp) // Tamaño del marco de la imagen
                .clip(shape = RoundedCornerShape(8.dp))
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
        ) {
            AsyncImage(
                model = imagen,
                contentDescription = "Imagen",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(8.dp))
            )
        }

        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = titulo,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 12.dp, bottom = 18.dp)
            )
            Text(
                text = descripcion,
                style = TextStyle(fontSize = 14.sp)
            )
        }
    }
}

fun String.limitTextLength(maxLength: Int): String {
    return if (this.length > maxLength) {
        "${this.take(maxLength)}..."
    } else {
        this
    }
}

