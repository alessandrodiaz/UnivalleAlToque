package com.example.univallealtoque.ui


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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.univallealtoque.R

@Composable
fun HomePageScreen(modifier: Modifier){
    var categoryNames = listOf(
        "Gym",
        "Rumba",
        "Lectura",
        "Piscina",
        "In English",
        "Taekwondo")
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Benvenido de nuevo!",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 0.dp)

            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Próximos eventos!",
                textAlign = TextAlign.Start,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 0.dp)

            )
            Spacer(modifier = Modifier.height(16.dp))
            CardComponent("Esta semana", listOf(),Modifier.size(width = 380.dp, height = 280.dp))
            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre las tarjetas
            CardComponent("¿Qué querés hacer?",categoryNames,Modifier.size(width = 380.dp, height = 248.dp))
            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre las tarjetas
            CardComponent("Semilleros", listOf(),Modifier.size(width = 380.dp, height = 248.dp))
            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre las tarjetas
        }
    }
}
@Composable
fun CardComponent(myText: String,categoryNames:  List<String>,modifier: Modifier) {
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
                .padding(8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 68.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement= Arrangement.spacedBy(40.dp),
            contentPadding = PaddingValues(start = 40.dp, end = 40.dp)
        ){
            items (categoryNames){ item ->
                val imageRes = when (item) {
                    "Gym" -> R.drawable.a // Reemplaza con el recurso adecuado
                    "Rumba" -> R.drawable.b // Reemplaza con el recurso adecuado
                    "Lectura" -> R.drawable.c // Reemplaza con el recurso adecuado
                    "Piscina" -> R.drawable.d // Reemplaza con el recurso adecuado
                    "In English" -> R.drawable.e // Reemplaza con el recurso adecuado
                    "Taekwondo" -> R.drawable.f // Reemplaza con el recurso adecuado
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
fun ImageAndTextComponent(imageRes: Int, text:String,modifier: Modifier){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
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
