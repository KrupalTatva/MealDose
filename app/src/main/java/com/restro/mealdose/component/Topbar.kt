package com.example.composedemo.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onBackPressed: (() -> Unit)? = null,
    isBackButton: Boolean = false,
    actions: @Composable RowScope.() -> Unit = {},
    textColor: Color = Color.White,
    backIconColor: Color = Color.Black
) {
    Surface(shadowElevation = 3.dp) {
        TopAppBar(
            modifier = modifier,
            title = {
                Text(
                    text = title,
                    style = TextStyle(
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    ),
                    modifier = Modifier.padding(horizontal = 14.dp)
                )
            },
            navigationIcon = {
                if (onBackPressed != null) {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = backIconColor,
                            modifier = Modifier.padding(0.dp).size(40.dp)
                        )
                    }
                }
            },
            actions = actions,
        )
    }
}

