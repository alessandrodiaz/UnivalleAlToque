package com.example.univallealtoque.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.univallealtoque.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoverPasswordScreen(
    modifier: Modifier
) {
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(
            text = stringResource(R.string.recoverpassword_title),
            style = MaterialTheme.typography.displayLarge,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 28.dp)
        )

        OutlinedTextField(
            value = password,
            textStyle = TextStyle(
                color = Color.Black
            ),
            onValueChange = { password = it },
            label = { Text(text = stringResource(id = R.string.register_email)) },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()

        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {
                println("jeje")
            },
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .height(52.dp)
                .background(
                    color = Color.Red,
                    shape = RoundedCornerShape(12.dp) // Ajusta el valor para redondear las esquinas
                )

        ) {
            Text(
                text = "Enviar",
                style = MaterialTheme.typography.displaySmall,
                color = Color.White,
            )
        }

    }


}