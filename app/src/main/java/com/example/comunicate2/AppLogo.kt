package com.example.comunicate2

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun AppLogo(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Logo de la aplicación",
        modifier = modifier.size(150.dp)
    )
}
