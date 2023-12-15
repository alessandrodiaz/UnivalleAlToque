package com.example.univallealtoque.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.univallealtoque.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.univallealtoque.activities.EnrolledActivitiesViewModel
import com.example.univallealtoque.activities.GetEventsViewModel
import com.example.univallealtoque.model.EnrolledActivitiesModel
import com.example.univallealtoque.model.EventsList
import com.example.univallealtoque.ui.components.Greeting

data class Semilleros(val name: String, val time: String, val imageRes: Int)

@Composable
fun HomePageScreen(
    modifier: Modifier,
) {
    val getEventsModel: GetEventsViewModel = viewModel()
    val getEventsState by getEventsModel.state.collectAsState()
    val eventsList by getEventsModel.events.collectAsState()

//    getEventsModel.resetState()

    LaunchedEffect(key1 = getEventsState.isRequestSuccessful) {
        if (!getEventsState.isListObtained && !getEventsState.isRequestSuccessful) {
            val response = getEventsModel.getEvents()
            Log.d("LISTA ACTIVIDADES: ", response.toString())
        }
    }

    var categoryNames = listOf(
        stringResource(id = R.string.gym),
        stringResource(id = R.string.rumba),
        stringResource(id = R.string.lectura),
        stringResource(id = R.string.piscina),
        stringResource(id = R.string.in_english),
        stringResource(id = R.string.taekwondo),
    )

    val semilleros = listOf(
        Semilleros(
            stringResource(id = R.string.futbol),
            stringResource(id = R.string.futbol_time),
            R.drawable.futbol
        ),
        Semilleros(
            stringResource(id = R.string.natacion),
            stringResource(id = R.string.natacion_time),
            R.drawable.natacion
        ),
        Semilleros(
            stringResource(id = R.string.rugby),
            stringResource(id = R.string.rugby_time),
            R.drawable.rugby
        ),
    )

    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            //SALUDO
            Greeting()
            Spacer(modifier = Modifier.height(16.dp))

            //TITULO
            Text(
                text = stringResource(id = R.string.proximos_eventos),
                textAlign = TextAlign.Start,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 0.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            //EVENTOS

            if (getEventsState.isListObtained) {
                EventsComponent(
                    events = eventsList,
                    stringResource(id = R.string.esta_semana),
                    Modifier.size(width = 380.dp, height = 280.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre las tarjetas

            //SECCIONES
            CardComponent(
                stringResource(id = R.string.que_queres_hacer),
                categoryNames,
                Modifier.size(width = 380.dp, height = 248.dp)
            )
            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre las tarjetas

            //SEMILLEROS
            CardComponent(
                stringResource(id = R.string.semilleros),
                listOf(),
                Modifier.size(width = 380.dp, height = 30.dp)
            )
            semilleros.forEach { semillero ->
                SemilleroItem(semillero = semillero, modifier = Modifier)
            }
            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre las tarjetas
        }
    }

}

@Composable
fun SemilleroItem(semillero: Semilleros, modifier: Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable { /* Acción cuando se hace clic en el semillero */ }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = semillero.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp) // Ajusta la altura de la imagen
            )

            // Nombre y hora del semillero
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = semillero.name, fontSize = 16.sp)
                Text(text = semillero.time, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}


@Composable
fun CardComponent(myText: String, categoryNames: List<String>, modifier: Modifier) {
    // Aquí puedes definir el contenido y la apariencia de tu tarjeta
    // Puedes usar el composable Card y personalizarlo según tus necesidades

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        //border = BorderStroke(2.dp,Color.Gray),
        modifier = modifier

    ) {
        Text(
            text = myText,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 68.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(40.dp),
            contentPadding = PaddingValues(start = 40.dp, end = 40.dp)
        ) {
            items(categoryNames) { item ->
                val imageRes = when (item) {
                    stringResource(id = R.string.gym) -> R.drawable.a // Reemplaza con el recurso adecuado
                    stringResource(id = R.string.rumba) -> R.drawable.b // Reemplaza con el recurso adecuado
                    stringResource(id = R.string.lectura) -> R.drawable.c // Reemplaza con el recurso adecuado
                    stringResource(id = R.string.piscina) -> R.drawable.d // Reemplaza con el recurso adecuado
                    stringResource(id = R.string.in_english) -> R.drawable.e // Reemplaza con el recurso adecuado
                    stringResource(id = R.string.taekwondo) -> R.drawable.f // Reemplaza con el recurso adecuado
                    else -> R.drawable.c // Reemplaza con una imagen predeterminada
                }
                ImageAndTextComponent(
                    imageRes = imageRes,
                    text = item,
                    modifier = Modifier
                )
            }
        }

    }
}

@Composable
fun ImageAndTextComponent(imageRes: Int, text: String, modifier: Modifier) {
    Column(
        modifier = modifier.padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .clip(RoundedCornerShape(1000.dp))
        )
        Text(
            text = text,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
                .height(18.dp),
        )
    }
}

@Composable
fun EventsComponent(events: List<EventsList>, myText: String, modifier: Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = modifier

    ) {
        Text(
            text = myText,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
        )

        val chunkedEvents =
            events.chunked(events.size / 2) // Dividir la lista de eventos en dos sublistas

        chunkedEvents.forEach { chunk ->
            LazyRow(
                contentPadding = PaddingValues(8.dp)
            ) {
                items(chunk) { event ->
                    Card(
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        AsyncImage(
                            model = event.photo,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(114.dp)
                        )
                    }
                }
            }
        }
    }
}
