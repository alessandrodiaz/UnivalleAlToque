package com.example.univallealtoque.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.univallealtoque.R
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.univallealtoque.UnivalleAlToqueScreen
import androidx.compose.foundation.lazy.LazyColumn
@Composable
fun TermsAndConditionsScreen(
    modifier: Modifier
) {
    // Reemplaza el Column con un LazyColumn
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        item {
            TitleTextComponent(value = stringResource(id = R.string.terms_conditions_title))
            Spacer(modifier = Modifier.height(35.dp))
            HeadingTextComponent(value = stringResource(id = R.string.terms_conditions_1_title))
            Spacer(modifier = Modifier.height(10.dp))
            TextComponent(value = stringResource(id = R.string.terms_conditions_1), padding = 0.dp)
            Spacer(modifier = Modifier.height(15.dp))
            HeadingTextComponent(value = stringResource(id = R.string.terms_conditions_2_title))
            Spacer(modifier = Modifier.height(10.dp))
            TextComponent(value = stringResource(id = R.string.terms_conditions_2_1), padding = 12.dp)
            Spacer(modifier = Modifier.height(10.dp))
            TextComponent(value = stringResource(id = R.string.terms_conditions_2_2), padding = 12.dp)
            Spacer(modifier = Modifier.height(15.dp))
            HeadingTextComponent(value = stringResource(id = R.string.terms_conditions_3_title))
            Spacer(modifier = Modifier.height(10.dp))
            TextComponent(value = stringResource(id = R.string.terms_conditions_3_1), padding = 12.dp)
            Spacer(modifier = Modifier.height(10.dp))
            TextComponent(value = stringResource(id = R.string.terms_conditions_3_2), padding = 12.dp)
            Spacer(modifier = Modifier.height(15.dp))
            HeadingTextComponent(value = stringResource(id = R.string.terms_conditions_4_title))
            Spacer(modifier = Modifier.height(10.dp))
            TextComponent(value = stringResource(id = R.string.terms_conditions_4_1), padding = 12.dp)
            Spacer(modifier = Modifier.height(10.dp))
            TextComponent(value = stringResource(id = R.string.terms_conditions_4_2), padding = 12.dp)
            Spacer(modifier = Modifier.height(15.dp))
            HeadingTextComponent(value = stringResource(id = R.string.terms_conditions_5_title))
            Spacer(modifier = Modifier.height(10.dp))
            TextComponent(value = stringResource(id = R.string.terms_conditions_5), padding = 0.dp)
            Spacer(modifier = Modifier.height(15.dp))
            HeadingTextComponent(value = stringResource(id = R.string.terms_conditions_6_title))
            Spacer(modifier = Modifier.height(10.dp))
            TextComponent(value = stringResource(id = R.string.terms_conditions_6), padding = 0.dp)
            Spacer(modifier = Modifier.height(15.dp))
            HeadingTextComponent(value = stringResource(id = R.string.terms_conditions_7_title))
            Spacer(modifier = Modifier.height(10.dp))
            TextComponent(value = stringResource(id = R.string.terms_conditions_7), padding = 0.dp)
            Spacer(modifier = Modifier.height(15.dp))
            HeadingTextComponent(value = stringResource(id = R.string.terms_conditions_8_title))
            Spacer(modifier = Modifier.height(10.dp))
            TextComponent(value = stringResource(id = R.string.terms_conditions_8), padding = 0.dp)
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
fun TitleTextComponent(value: String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp)
            .padding(top = 55.dp),
        style = androidx.compose.ui.text.TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal),
        color = colorResource(id = R.color.colorText),
        textAlign = TextAlign.Center
    )
}

@Composable
fun HeadingTextComponent(value: String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth(),

        style = androidx.compose.ui.text.TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal),
        color = colorResource(id = R.color.colorText),
        textAlign = TextAlign.Left
    )
}

@Composable
fun TextComponent(value: String, padding: Dp){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp)
            .padding(start = padding),
        style = androidx.compose.ui.text.TextStyle(
            fontSize = 17.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal),
        color = colorResource(id = R.color.colorText),
        textAlign = TextAlign.Left
    )
}
