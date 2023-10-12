 package com.example.univallealtoque

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.univallealtoque.theme.UnivalleAlToqueTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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