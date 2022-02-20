package com.example.ahbassignment

/**
 * Created by Shaheer cs on 20/02/2022.
 */

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

val Typography = Typography(
    h1 = TextStyle(fontSize = 50.sp, fontWeight = FontWeight.Bold,color = Color.Red),
    body1 = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center)
)