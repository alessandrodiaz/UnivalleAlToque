package com.example.univallealtoque.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.univallealtoque.ui.RegisterScreen
import com.example.univallealtoque.ui.TermsAndConditionsScreen
import androidx.navigation.compose.rememberNavController

sealed class Screen(){
    object HomeScreen : Screen()
    object TermsAndConditionsScreen: Screen()
}

object PostOfficeAppRouter {

    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.HomeScreen)

    fun navigateTo(destination: Screen){
        currentScreen.value = destination
    }

}
@Composable
fun PostOfficeApp( navController: NavHostController = rememberNavController()) {

    Surface (
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Crossfade(targetState = PostOfficeAppRouter.currentScreen) {
            currentState ->
            when(currentState.value){
                is Screen.HomeScreen -> {
                    RegisterScreen(

                        navController = navController,
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxSize()
                            .padding(16.dp),
                    )
                }
                is Screen.TermsAndConditionsScreen -> {
                    TermsAndConditionsScreen(

                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxSize()
                            .padding(16.dp),
                    )
                }
            }
        }
    }
}