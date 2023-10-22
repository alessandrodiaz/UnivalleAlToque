package com.example.univallealtoque.presentation.sign_in

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.univallealtoque.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    state: SignInState,
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }


    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(
            text = stringResource(R.string.login_title),
            style = MaterialTheme.typography.displayLarge,
            color = Color.Black
        )
        Spacer(
            modifier = modifier.size(40.dp)
        )
        Text(
            text = stringResource(R.string.login_description),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displaySmall,
            color = Color.Black
        )
        Spacer(
            modifier = modifier.size(40.dp)
        )


        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .height(height = 52.dp)
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(color = Color.White)
        )

        Spacer(
            modifier = modifier.size(20.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { text = it },
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .height(height = 52.dp)
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(color = Color.White),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(
            modifier = modifier.size(20.dp)
        )

        Button(
            onClick = { },
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
        ) {
            Text(
                text = stringResource(R.string.login_title),
                style = MaterialTheme.typography.displaySmall,
            )
        }

        Spacer(
            modifier = modifier.size(40.dp)
        )

        Text(
            text = stringResource(R.string.login_google_description),
            style = MaterialTheme.typography.displaySmall,
        )

        Spacer(
            modifier = modifier.size(20.dp)
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(26.dp))
                .background(MaterialTheme.colorScheme.primary)
                .clickable { onSignInClick() }
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = null,
                    modifier = Modifier
                        .size(38.dp)
                        .padding(6.dp)
                )
                Text(
                    text = stringResource(R.string.login_button),
                    color = Color.White,

                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier
                        .padding(6.dp)
                )
            }
        }

        Button(
            onClick = { },
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
        ) {
            Text(
                text = stringResource(R.string.login_title),
                style = MaterialTheme.typography.displaySmall,
            )
        }
        Spacer(
            modifier = modifier.size(50.dp)
        )
    }
}