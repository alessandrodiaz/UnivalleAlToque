package com.example.univallealtoque.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.content.Context
import com.example.univallealtoque.sign_in.SignInViewModel
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.univallealtoque.R
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    coroutineScope: CoroutineScope,
    viewModel: SignInViewModel,
    context: Context,
    modifier: Modifier = Modifier.fillMaxSize()
) {

    Column(
        modifier = modifier,
            /*.fillMaxWidth()
            .fillMaxSize()
            .padding(8.dp)
            .background(color=Color.Black),*/
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally){
            Spacer(
                modifier = modifier.size(80.dp)
            )

            Text(
                text = stringResource(R.string.register_title),
                style = MaterialTheme.typography.displayLarge,
                color = Color.Black
            )
            Spacer(
                modifier = modifier.size(40.dp)
            )

            /*OutlinedTextField(
                value = text,
                textStyle = TextStyle(
                    color = Color.Black
                ),
                onValueChange = { text = it },
                label = { Text(text= stringResource(id = R.string.login_email)) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            )*/


            Spacer(
                modifier = modifier.size(10.dp)
            )

            /*OutlinedTextField(
                value = password,
                textStyle = TextStyle(
                    color = Color.Black
                ),
                onValueChange = { password = it },
                label = { Text(text= stringResource(id = R.string.login_password)) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
            )*/

            Spacer(
                modifier = modifier.size(20.dp)
            )

            Box(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .height(52.dp)
                    .background(
                        color = Color.Red,
                        shape = RoundedCornerShape(16.dp) // Ajusta el valor para redondear las esquinas
                    )
            ) {
                Button(
                    onClick = { /* Tu acción al hacer clic */ },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = stringResource(R.string.login_title),
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.White // Color del texto
                    )
                }
            }
            Box(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .height(52.dp)
                    .background(
                        color = Color.Red,
                        shape = RoundedCornerShape(16.dp) // Ajusta el valor para redondear las esquinas
                    )
            ) {
                Button(
                    onClick = { /* Tu acción al hacer clic */ },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = stringResource(R.string.login_title),
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.White // Color del texto
                    )
                }
            }
            Box(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .height(52.dp)
                    .background(
                        color = Color.Red,
                        shape = RoundedCornerShape(16.dp) // Ajusta el valor para redondear las esquinas
                    )
            ) {
                Button(
                    onClick = { /* Tu acción al hacer clic */ },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = stringResource(R.string.login_title),
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.White // Color del texto
                    )
                }
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

            /*Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
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
            }*/

            Spacer(
                modifier = modifier.size(50.dp)
            )

            Row(){
                Text(
                    text = stringResource(R.string.login_register_description),
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier
                        .padding(end = 16.dp)
                )

                Text(
                    text = stringResource(R.string.login_register),
                    style = MaterialTheme.typography.titleSmall,
                    color= Color.Red
                )
            }

            Spacer(
                modifier = modifier.size(40.dp)
            )
        }

    }
}