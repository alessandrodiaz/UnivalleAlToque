package com.example.univallealtoque

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupCupUnivalleNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            UnivalleAlToqueApp(navController = navController)
        }
    }

    @Test
    fun univalleAlToque_verifyStartDestination() {
        navController.assertCurrentRouteName(UnivalleAlToqueScreen.HomePage.name)
    }

    @Test
    fun univalleAlToque_NavigatesToProfile() {
        composeTestRule.onNodeWithContentDescription("Boton perfil")
            .performClick()
        navController.assertCurrentRouteName(UnivalleAlToqueScreen.Login.name)
    }

    @Test
    fun univalleAlToque_NavigatesFromProfileToHome() {
        composeTestRule.onNodeWithContentDescription("Boton perfil")
            .performClick()
        composeTestRule.onNodeWithContentDescription("Boton home")
            .performClick()
        navController.assertCurrentRouteName(UnivalleAlToqueScreen.HomePage.name)


    }


}