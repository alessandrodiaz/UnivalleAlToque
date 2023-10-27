package com.example.univallealtoque.ui

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import android.app.Activity.RESULT_OK
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.collectAsState
import com.example.univallealtoque.sign_in.GoogleAuthUiClient
import com.example.univallealtoque.sign_in.SignInViewModel
import com.example.univallealtoque.sign_in.SignInState
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.launch
import androidx.navigation.NavController
import com.example.univallealtoque.UnivalleAlToqueScreen
import com.example.univallealtoque.model.UserData
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navController: NavController,
    signInState: SignInState,
    coroutineScope: CoroutineScope,
    viewModel: SignInViewModel,
    googleAuthUiClient: GoogleAuthUiClient,
    context: Context,
    modifier: Modifier = Modifier.fillMaxSize()
) {



    /*val viewModel = viewModel<SignInViewModel>()*/
    val state by viewModel.state.collectAsState()


    LaunchedEffect(key1 = Unit) {
        if (googleAuthUiClient.getSignedInUser() != null) {
            navController.navigate(UnivalleAlToqueScreen.Profile.name)
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            coroutineScope.launch {
                val signInResult = googleAuthUiClient.signInWithIntent(
                    intent = result.data ?: return@launch
                )
                viewModel.onSignInResult(signInResult)
            }
        }
    }

    LaunchedEffect(key1 = signInState.isSignInSuccessful) {
        if (signInState.isSignInSuccessful) {
            Toast.makeText(
                context,
                "Sign in successful",
                Toast.LENGTH_LONG
            ).show()

            navController.navigate(UnivalleAlToqueScreen.Profile.name)
            viewModel.resetState()
        }
    }
    SignInScreen(
        navigateRegister = { navController.navigate(UnivalleAlToqueScreen.Register.name)},
        navController= navController,
        state = state,
        onSignInClick = {
            coroutineScope.launch {
                val signInIntentSender = googleAuthUiClient.signIn()
                launcher.launch(
                    IntentSenderRequest.Builder(
                        signInIntentSender ?: return@launch
                    ).build()
                )
            }
        }

    )
}
