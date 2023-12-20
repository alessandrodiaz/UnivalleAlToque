package com.example.univallealtoque.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.univallealtoque.R
import com.example.univallealtoque.activities.SemillerosVIewModel
import com.example.univallealtoque.data.AppDataStoreSingleton
import com.example.univallealtoque.data.DataStoreSingleton
import com.example.univallealtoque.model.SemilleroModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SemilleroScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val semillerosVIewModel: SemillerosVIewModel = viewModel()
    val semillerosSatate by semillerosVIewModel.state.collectAsState()
    val semillerosList by semillerosVIewModel.activities.collectAsState()

    val isEnrolled = semillerosSatate.isEnrolled

    //USER DATA FROM DATASTORE
    val userDataFlow = DataStoreSingleton.getUserData().collectAsState(initial = null)
    val userCode = userDataFlow.value?.user_id?.toString() ?: "null"

    //GET APP DATA
    val appDataFlow = AppDataStoreSingleton.getAppData().collectAsState(initial = null)
    val imageID = appDataFlow.value?.toString() ?: "no_id_provided"

    Log.d("ID IMAGEN", imageID)

    //GET ACTIVITIES
    // Utilizando LaunchedEffect para ejecutar la lógica una vez al ingresar a la pantalla
    if (userCode != null && userCode != "null") {
        LaunchedEffect(key1 = semillerosSatate.isRequestSuccessful) {
            if (!semillerosSatate.isRequestSuccessful) {
                val data = SemilleroModel(userCode, semillero_id = imageID)
                val response = semillerosVIewModel.semilleroInfo(data)
            }
        }
    }

    val isLoading by semillerosVIewModel.isLoading.collectAsState()

    if (isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp),
                color = Color.Red// Puedes ajustar el tamaño del indicador según tus necesidades
            )
        }
    } else {
        val name = semillerosList.group_name ?: "Seminario 1"
        val description = semillerosList.group_description
            ?: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In pulvinar pulvinar fermentum. Nam tincidunt viverra ligula, in tristique dui condimentum eget. Phasellus id tincidunt mauris, in lacinia ante. Pellentesque eleifend augue sit amet mi suscipit fermentum. Etiam dignissim laoreet sollicitudin. Mauris ut lectus nisin"
        val slots = semillerosList.slots ?: "5"
        val availableSlots = semillerosList.available_slots ?: "5"
        val photo = semillerosList.photo
            ?: "https://www.eltiempo.com/files/image_640_428/uploads/2017/09/10/59b5e27b68ea7.jpeg"
        val place = semillerosList.place ?: "Plazoleta"

        data class DayInfo(
            val dayName: String,
            val startTime: String?,
            val endTime: String?
        )

        val daysOfWeek = listOf(
            DayInfo(
                stringResource(id = R.string.monday),
                semillerosList.monday_start ?: "null",
                semillerosList.monday_end ?: "null"
            ),
            DayInfo(
                stringResource(id = R.string.tuesday),
                semillerosList.tuesday_start ?: "null",
                semillerosList.tuesday_end ?: "null"
            ),
            DayInfo(
                stringResource(id = R.string.wednesday),
                semillerosList.wednesday_start ?: "null",
                semillerosList.wednesday_end ?: "null"
            ),
            DayInfo(
                stringResource(id = R.string.thursday),
                semillerosList.thursday_start ?: "null",
                semillerosList.thursday_end ?: "null"
            ),
            DayInfo(
                stringResource(id = R.string.friday),
                semillerosList.friday_start ?: "null",
                semillerosList.friday_end ?: "null"
            ),
            DayInfo(
                stringResource(id = R.string.saturday),
                semillerosList.saturday_start ?: "null",
                semillerosList.saturday_end ?: "null"
            )
        )



        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.TopStart)
            ) {
                item {
                    // IMAGEN SUPERIOR
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 11)
                    ) {
                        AsyncImage(
                            model = photo,
                            contentDescription = "Imagen",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        Text(
                            text = name,
                            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(
                                top = 12.dp,
                                bottom = 18.dp,
                                start = 12.dp,
                                end = 12.dp
                            )
                        )
                        Text(
                            text = description,
                            style = TextStyle(fontSize = 16.sp),
                            modifier = Modifier.padding(
                                top = 12.dp,
                                bottom = 18.dp,
                                start = 14.dp,
                                end = 14.dp
                            )
                        )

                        Row {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = stringResource(R.string.content_description_home),
                                modifier = Modifier
                                    .size(70.dp)
                                    .padding(horizontal = 10.dp),
                                tint = Color.Black,
                            )
                            Text(
                                text = "$availableSlots/$slots",
                                style = TextStyle(fontSize = 16.sp),
                                modifier = Modifier.padding(
                                    top = 22.dp,
                                    bottom = 18.dp,
                                    start = 10.dp,
                                    end = 10.dp
                                )
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                        {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = stringResource(R.string.content_description_home),
                                modifier = Modifier
                                    .size(70.dp)
                                    .padding(horizontal = 10.dp),
                                tint = Color.Black,
                            )
                            Column {
                                for (day in daysOfWeek) {
                                    if (day.startTime != "null" && day.endTime != "null") {
                                        Row {
                                            Text(
                                                text = day.dayName,
                                                style = TextStyle(fontSize = 16.sp),
                                                modifier = Modifier.padding(
                                                    end = 10.dp,
                                                    start = 10.dp
                                                )
                                            )
                                            Text(
                                                text = "${day.startTime} - ${day.endTime}",
                                                style = TextStyle(fontSize = 16.sp),
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Row {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = stringResource(R.string.content_description_home),
                                modifier = Modifier
                                    .size(70.dp)
                                    .padding(horizontal = 10.dp),
                                tint = Color.Black,
                            )
                            Text(
                                text = place,
                                style = TextStyle(fontSize = 16.sp),
                                modifier = Modifier.padding(
                                    top = 22.dp,
                                    bottom = 18.dp,
                                    start = 10.dp,
                                    end = 10.dp
                                )
                            )
                        }

                        Log.d("ENROLLED",isEnrolled.toString())

                        // SI NO ESTA INSCRITO
                        if (!isEnrolled) {
                            Button(
                                onClick = {

                                },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .fillMaxHeight()
                                    .padding(top = 20.dp, bottom = 80.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)

                            ) {
                                Text(
                                    text = stringResource(R.string.register_semillero),
                                    style = MaterialTheme.typography.displaySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        } else { // SI ESTA INSCRITO
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentSize(Alignment.Center)
                                    .padding(top = 35.dp, bottom = 5.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.already_Enrolled_semillero),
                                    style = MaterialTheme.typography.displaySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                )
                            }
                            Button(
                                onClick = {


                                },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .fillMaxHeight()
                                    .padding(top = 20.dp, bottom = 80.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)

                            ) {
                                Text(
                                    text = stringResource(id = R.string.semillero_Unrolled),
                                    style = MaterialTheme.typography.displaySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}



