package com.example.univallealtoque.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.univallealtoque.presentation.sign_in.UserData
import com.example.univallealtoque.ui.components.Greeting

@Composable
fun HomePageScreen(
    modifier: Modifier,
    userData: UserData?
){
    Greeting(userData = userData)
}

