package com.example.univallealtoque

import android.annotation.SuppressLint
import android.os.Build
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
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.univallealtoque.data.DataStoreSingleton
import com.example.univallealtoque.model.UserDataResponseExpress
import com.example.univallealtoque.ui.PrivacyPolicyScreen
import com.example.univallealtoque.sign_in_express.LoginViewModelExpress
import com.example.univallealtoque.sign_in_google.GoogleAuthUiClient
import com.example.univallealtoque.sign_in_express.LoginState
import com.example.univallealtoque.sign_in_google.SignInViewModel
import com.example.univallealtoque.ui.CreateNewActivityScreen
import com.example.univallealtoque.ui.DeleteUserScreen
import com.example.univallealtoque.ui.GetCodeScreen
import com.example.univallealtoque.ui.MyGroupsScreen
import com.example.univallealtoque.ui.ProfileScreen
import com.example.univallealtoque.ui.RecoverPasswordScreen
import com.example.univallealtoque.ui.RegisterScreen
import com.example.univallealtoque.ui.SettingsScreen
import com.example.univallealtoque.ui.TermsAndConditionsScreen
import com.example.univallealtoque.ui.NewPasswordScreen


enum class UnivalleAlToqueScreen(@StringRes val title: Int) {
    HomePage(title = R.string.app_name),
    Login(title = R.string.login),
    Profile(title = R.string.profile),
    Register(title = R.string.register),
    TermsAndConditions(title = R.string.terms),
    PrivacyPolicy(title = R.string.privacypolicy),
    RecoverPassword(title = R.string.recoverpassword),
    GetCode(title = R.string.getcode),
    Settings(title = R.string.settings),
    DeleteUser(title = R.string.delete_account),
    NewPassword(title = R.string.new_password),
    CreateNewActivity(title = R.string.create_new_activity),
    MyGroups(title = R.string.my_groups_title)
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
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),

        title = {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = colorResource(id = R.color.red)),
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.padding(
                        top = 6.dp,
                        bottom = 6.dp
                    ),
                )
            }
        },

        navigationIcon = {
            if (false) {//canNavigateBack shange if arrow back wanted
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
    navigateLogin: () -> Unit,
    navigateHome: () -> Unit,
    navigateProfile: () -> Unit,
    navigateCreateActivity: () -> Unit,
    navigateMyGroups: () -> Unit,
    userModelExpressState: LoginState,
) {
    val userDataFlow = DataStoreSingleton.getUserData().collectAsState(initial = null)
    val userDataEmail = userDataFlow.value?.email
    // Íconos de navegación
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.red))
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = navigateHome
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = stringResource(R.string.content_description_home),
                modifier = Modifier.size(32.dp),
                tint = Color.White,
            )
        }
        IconButton(
            onClick = navigateCreateActivity
        ) {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = Color.White,
            )

        }
        IconButton(
            onClick = navigateMyGroups
        ) {

            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = Color.White,
            )
        }
        IconButton(
            onClick = if (userDataEmail != null) navigateProfile else navigateLogin,
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = stringResource(R.string.content_description_profile),
                modifier = Modifier.size(32.dp),
                tint = Color.White,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun UnivalleAlToqueApp(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current

    val googleAuthUiClient by remember {
        mutableStateOf(
            GoogleAuthUiClient(
                context = context,
                oneTapClient = Identity.getSignInClient(context)
            )
        )
    }
    //Create NavController
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = UnivalleAlToqueScreen.valueOf(
        backStackEntry?.destination?.route ?: UnivalleAlToqueScreen.HomePage.name
    )

    val coroutineScope = rememberCoroutineScope()
    val viewModel: SignInViewModel = viewModel()
    val signInState by viewModel.state.collectAsState()

    val loginViewModelExpress: LoginViewModelExpress = viewModel()
    val loginViewModelExpressState by loginViewModelExpress.stateLoginExpress.collectAsState()

    Scaffold(
        topBar = {
            UnivalleAlToqueAppBar(
                currentScreenTitle = currentScreen.title,
                canNavigateBack = (navController.previousBackStackEntry != null) and (backStackEntry?.destination?.route != UnivalleAlToqueScreen.HomePage.name),
                navigateUp = { navController.navigateUp() })
        },
        bottomBar = {
            UnivalleAlToqueBottomBar(
                navigateLogin = { navController.navigate(UnivalleAlToqueScreen.Login.name) },
                navigateHome = { navController.navigate(UnivalleAlToqueScreen.HomePage.name) },
                navigateProfile = { navController.navigate(UnivalleAlToqueScreen.Profile.name) },
                navigateCreateActivity = { navController.navigate(UnivalleAlToqueScreen.CreateNewActivity.name) },
                navigateMyGroups = { navController.navigate(UnivalleAlToqueScreen.MyGroups.name) },
                userModelExpressState = loginViewModelExpressState,
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = UnivalleAlToqueScreen.CreateNewActivity.name,
        ) {

            composable(route = UnivalleAlToqueScreen.HomePage.name) {
                HomePageScreen(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                        .padding(innerPadding)
//                    userData = googleAuthUiClient.getSignedInUser()
                )
            }

            composable(route = UnivalleAlToqueScreen.Login.name) {
                LoginScreen(
                    navController = navController,
                    signInState = signInState,
                    coroutineScope = coroutineScope,
                    viewModel = viewModel,
                    userModelExpress = loginViewModelExpress,
                    googleAuthUiClient = googleAuthUiClient,
                    context = context
                )
            }

            composable(route = UnivalleAlToqueScreen.Profile.name) {
                ProfileScreen(
                    userDataGoogle = googleAuthUiClient.getSignedInUser(),
                    onSignOut = {
                        coroutineScope.launch {
                            DataStoreSingleton.saveUserData(
                                UserDataResponseExpress(
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null
                                )
                            )

//                            googleAuthUiClient.signOut()
//                            Toast.makeText(
//                                context,
//                                R.string.signed_out,
//                                Toast.LENGTH_LONG
//                            ).show()
//
//                            navController.popBackStack()
                        }
                    },
                    userModelExpress = loginViewModelExpress,
                    navController = navController,
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                        .padding(innerPadding),
                )
            }

            composable(route = UnivalleAlToqueScreen.Register.name) {
                RegisterScreen(

                    navController = navController,
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                        .padding(innerPadding),
                )
            }

            composable(route = UnivalleAlToqueScreen.TermsAndConditions.name) {
                TermsAndConditionsScreen(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                        .padding(innerPadding),
                )
            }

            composable(route = UnivalleAlToqueScreen.PrivacyPolicy.name) {
                PrivacyPolicyScreen(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                        .padding(innerPadding),
                )
            }

            composable(route = UnivalleAlToqueScreen.RecoverPassword.name) {
                RecoverPasswordScreen(
                    navController = navController,
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                        .padding(innerPadding),
                )
            }

            composable(route = UnivalleAlToqueScreen.GetCode.name) {
                GetCodeScreen(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                        .padding(innerPadding),
                )
            }

            composable(route = UnivalleAlToqueScreen.Settings.name) {
                SettingsScreen(
                    navController = navController,
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                        .padding(innerPadding),
                )
            }

            composable(route = UnivalleAlToqueScreen.DeleteUser.name) {
                DeleteUserScreen(
                    navController = navController,
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                        .padding(innerPadding),
                    onSignOut = {
                        coroutineScope.launch {
                            DataStoreSingleton.saveUserData(
                                UserDataResponseExpress(
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null
                                )
                            )
                        }
                    },
                )
            }
            composable(route = UnivalleAlToqueScreen.NewPassword.name) {
                NewPasswordScreen(
                    navController = navController,
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                        .padding(innerPadding),
                    userEmail = "",
                )
            }

            composable(route = UnivalleAlToqueScreen.CreateNewActivity.name){
                CreateNewActivityScreen(
                    userModelExpress = loginViewModelExpress,
                    navController = navController,
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                        .padding(16.dp),
                )
            }

            composable(route = UnivalleAlToqueScreen.MyGroups.name){
                MyGroupsScreen(
                    navController = navController,
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                        .padding(innerPadding),
                )
            }
        }
    }
}
