package com.example.univallealtoque

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.univallealtoque.ui.HomePageScreen
import com.example.univallealtoque.ui.LoginScreen
import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
/*import com.example.univallealtoque.presentation.profile.ProfileScreen*/
import com.example.univallealtoque.presentation.sign_in.GoogleAuthUiClient
import com.example.univallealtoque.presentation.sign_in.SignInScreen
import com.example.univallealtoque.presentation.sign_in.SignInViewModel
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.example.univallealtoque.presentation.sign_in.SignInScreen
import kotlinx.coroutines.launch
import kotlin.math.sign

enum class UnivalleAlToqueScreen(@StringRes val title: Int) {
    HomePage(title = R.string.app_name),
    Login(title = R.string.login),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnivalleAlToqueAppBar(
    @StringRes currentScreenTitle: Int,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreenTitle),style = MaterialTheme.typography.displaySmall,) },
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun UnivalleAlToqueBottomBar(
    navigateLogin: () -> Unit
){
    // Íconos de navegación
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Red)
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        IconButton(
            onClick = {  }
        ) {
            Icon(imageVector = Icons.Default.Home, contentDescription = null, modifier = Modifier.size(32.dp))
        }
        IconButton(
            onClick = { /* Acción de navegación */ }
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null, modifier = Modifier.size(32.dp))
        }
        IconButton(
            onClick = { /* Acción adicional */ }
        ) {
            Icon(imageVector = Icons.Default.Favorite, contentDescription = null, modifier = Modifier.size(32.dp))
        }
        IconButton(
            onClick =  navigateLogin
        ) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null, modifier = Modifier.size(32.dp))
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UnivalleAlToqueApp(){
    //Create NavController
    val navController = rememberNavController()
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = UnivalleAlToqueScreen.valueOf(
        backStackEntry?.destination?.route ?: UnivalleAlToqueScreen.HomePage.name
    )

    /*val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }*/

    Scaffold(
        topBar = {
            UnivalleAlToqueAppBar(
                currentScreenTitle = currentScreen.title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() })
        },
        bottomBar = {
            UnivalleAlToqueBottomBar(navigateLogin = { navController.navigate(UnivalleAlToqueScreen.Login.name) })
        }
    ){innerPadding ->
        NavHost(
            navController = navController,
            startDestination = UnivalleAlToqueScreen.HomePage.name,
        ) {
            composable(route = UnivalleAlToqueScreen.HomePage.name) {
                HomePageScreen(
                     modifier = Modifier
                         .fillMaxSize()
                        .padding(innerPadding)
                    )
                }





            composable(route = UnivalleAlToqueScreen.Login.name) {
                LoginScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
                val viewModel = viewModel<SignInViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()

                /*LaunchedEffect(key1 = Unit) {
                    if(googleAuthUiClient.getSignedInUser() != null) {
                        navController.navigate("profile")
                    }
                }

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        if(result.resultCode == RESULT_OK) {
                            lifecycleScope.launch {
                                val signInResult = googleAuthUiClient.signInWithIntent(
                                    intent = result.data ?: return@launch
                                )
                                viewModel.onSignInResult(signInResult)
                            }
                        }
                    }
                )

                LaunchedEffect(key1 = state.isSignInSuccessful) {
                    if(state.isSignInSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "Sign in successful",
                            Toast.LENGTH_LONG
                        ).show()

                        navController.navigate("profile")
                        viewModel.resetState()
                    }
                }

                SignInScreen(
                    state = state,
                    onSignInClick = {
                        lifecycleScope.launch {
                            val signInIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    }
                )*/
            }
        }
    }
}
