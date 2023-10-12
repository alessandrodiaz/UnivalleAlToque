package com.example.univallealtoque.presentation.sign_in

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.univallealtoque.R

@Composable
fun SignInScreen(
    state: SignInState,
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {

        Text(
            text = stringResource(R.string.login_title),
            fontSize = 24.sp
            /*style = MaterialTheme.typography.displaySmall*/
        )
        Spacer(
            modifier = modifier.size(30.dp)
        )
        Text(
            text = stringResource(R.string.login_description),
            fontSize = 20.sp,
            textAlign = TextAlign.Center
            /*style = MaterialTheme.typography.displaySmall*/
        )
        Spacer(
            modifier = modifier.size(30.dp)
        )
        Button(onClick = onSignInClick) {
            Text(text = "Iniciar sesión con Google")
        }
    }


    //}
}