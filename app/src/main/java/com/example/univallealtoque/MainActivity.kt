 package com.example.univallealtoque

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowInsetsControllerCompat
import com.example.univallealtoque.data.AppDataStoreSingleton
import com.example.univallealtoque.data.DataStoreSingleton
import com.example.univallealtoque.theme.UnivalleAlToqueTheme
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


 class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataStoreSingleton.initialize(this)
        AppDataStoreSingleton.initialize(this)
        val mAuth = FirebaseAuth.getInstance();
        val user = mAuth.currentUser

        fun signInAnonymously() {
            mAuth.signInAnonymously().addOnSuccessListener(this, OnSuccessListener<AuthResult?> {
                // do your stuff
            })
                .addOnFailureListener(this,
                    OnFailureListener { exception -> Log.e(TAG, "signInAnonymously:FAILURE", exception) })
        }
        if (user != null) {
            // do your stuff
        } else {
            signInAnonymously()
        }
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = true
        setContent {
            UnivalleAlToqueTheme {
                    UnivalleAlToqueApp()
            }
        }
    }
}


 @Preview
 @Composable
 fun UnivalleAlToqueAppPreview() {
     UnivalleAlToqueApp()
 }

