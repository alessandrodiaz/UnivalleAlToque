 package com.example.univallealtoque

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.example.univallealtoque.network.AlToqueServiceFactory
import com.example.univallealtoque.theme.UnivalleAlToqueTheme
import kotlinx.coroutines.launch

 class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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