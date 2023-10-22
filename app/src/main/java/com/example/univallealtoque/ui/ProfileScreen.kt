package com.example.univallealtoque.ui

import android.graphics.Color.rgb
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.univallealtoque.R
import com.example.univallealtoque.presentation.sign_in.UserData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userData: UserData?,
    onSignOut: () -> Unit,
    modifier: Modifier
) {
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
    Column(
        modifier = modifier
    ) {
        CircleShape()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //PROFILE PICTURE
            if (userData?.profilePictureUrl != null) {
                AsyncImage(
                    model = userData.profilePictureUrl,
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
            if (userData?.username != null) {
                Text(
                    text = userData.username,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(25.dp))

            } else {
                Text(
                    text = "LUIS FELIPE",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "ZUÑIGA MALDONADO",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
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
            infoPart("Correo", "LUIS.ZUNIGA@CORREOUNIVALLE.EDU.CO")
            infoPart("Celular", "3134567890")
            infoPart("Carrera", "Ing. Sistemas")
            Spacer(modifier = Modifier.height(8.dp))
            IconButton(onClick = onSignOut) {
                Image(
                    painter = painterResource(id = R.drawable.logout),
                    contentDescription = stringResource(id = R.string.logout),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(40.dp)
                        .padding(borderWidth)
                        .clip(CircleShape)
                )
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
fun infoPart(infoName: String, infoValue: String) {
    var text by remember { mutableStateOf("") }
    Spacer(modifier = Modifier.height(16.dp))
    BasicTextField(
        value = infoName,
        onValueChange = { text = it },
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        ),
        modifier = Modifier
            .padding(start = 24.dp)
            .fillMaxWidth()
    )
    OutlinedTextField(
        value = infoValue,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        ),
        onValueChange = { text = text },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
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