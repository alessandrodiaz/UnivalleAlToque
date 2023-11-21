package com.example.univallealtoque.ui.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.univallealtoque.R
import com.example.univallealtoque.data.DataStoreSingleton
import com.example.univallealtoque.sign_in_google.UserData
import java.util.Locale

@Composable
fun Greeting(
){
    //Obtener datos del usuario del DataStore
    val userDataFlow = DataStoreSingleton.getUserData().collectAsState(initial = null)
    val nameToShow = userDataFlow.value?.name

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        if (nameToShow != null) {
            val formattedUsername = nameToShow
            Text(
                text = stringResource(R.string.greeting_username) + " $formattedUsername",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 20.dp)
            )
        } else {
            Text(
                text = stringResource(R.string.greeting_empty),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
    }

}

// Format username text
fun String.formatUsername(): String {
    val firstWord = this.split(" ").firstOrNull() ?: ""
    return firstWord.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}