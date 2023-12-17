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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.univallealtoque.activities.SemillerosVIewModel
import com.example.univallealtoque.data.DataStoreSingleton
import com.example.univallealtoque.model.EnrolledActivitiesModel
import com.example.univallealtoque.model.RegisterModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SemilleroScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val semillerosVIewModel: SemillerosVIewModel = viewModel()
    val semillerosSatate by semillerosVIewModel.state.collectAsState()
    val activitiesList by semillerosVIewModel.activities.collectAsState()

    //USER DATA FROM DATASTORE
    val userDataFlow = DataStoreSingleton.getUserData().collectAsState(initial = null)
    val userCode = userDataFlow.value?.user_id?.toString() ?: "null"
    var isEnrolled by remember { mutableStateOf(false) }


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
            item{
                AsyncImage(
                    model = "https://www.eltiempo.com/files/image_640_428/uploads/2017/09/10/59b5e27b68ea7.jpeg",
                    contentDescription = "Imagen",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    Text(
                        text = "Semilero 1",
                        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(top = 12.dp, bottom = 18.dp, start=12.dp, end=12.dp)
                    )
                    Text(
                        text = "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio.",
                        style = TextStyle(fontSize = 16.sp),
                        modifier = Modifier.padding(top = 12.dp, bottom = 18.dp, start=14.dp, end=14.dp)
                    )

                    Row {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = stringResource(R.string.content_description_home),
                            modifier = Modifier
                                .size(70.dp)
                                .padding(horizontal = 10.dp)
                                ,
                            tint = Color.Black,
                        )
                        Text(
                            text = "5/12",
                            style = TextStyle(fontSize = 16.sp),
                            modifier = Modifier.padding(top = 22.dp, bottom = 18.dp, start=10.dp, end=10.dp)
                        )
                    }

                    Row {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = stringResource(R.string.content_description_home),
                            modifier = Modifier
                                .size(70.dp)
                                .padding(horizontal = 10.dp)
                            ,
                            tint = Color.Black,
                        )
                        Text(
                            text = "Jueves a las 12",
                            style = TextStyle(fontSize = 16.sp),
                            modifier = Modifier.padding(top = 22.dp, bottom = 18.dp, start=10.dp, end=10.dp)
                        )
                    }

                    Row {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = stringResource(R.string.content_description_home),
                            modifier = Modifier
                                .size(70.dp)
                                .padding(horizontal = 10.dp)
                            ,
                            tint = Color.Black,
                        )
                        Text(
                            text = "Plazoleta",
                            style = TextStyle(fontSize = 16.sp),
                            modifier = Modifier.padding(top = 22.dp, bottom = 18.dp, start=10.dp, end=10.dp)
                        )
                    }
                }
                if (isEnrolled){
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
                }
                else {
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



