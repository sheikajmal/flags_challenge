package com.flagschallenge.app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flagschallenge.app.presentation.feature.ui.theme.outline
import com.flagschallenge.app.presentation.feature.ui.theme.theme
import com.flagschallenge.app.utils.Dimen
import com.flagschallenge.app.utils.Typo

@Composable
fun ActionBar(
    minutes: Long? = null,
    seconds: Long? = null
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Card(
                modifier = Modifier
                    .width(90.dp)
                    .fillMaxHeight(),
                shape = RoundedCornerShape(
                    topStart = Dimen.themeRadius,
                    topEnd = Dimen.themeRadius,
                    bottomEnd = Dimen.themeRadius
                ),
                elevation = CardDefaults.cardElevation(Dimen.elevation)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF000000),
                                    Color(0xFF333333),
                                    Color(0xFF000000)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (minutes == null && seconds == null)
                            "00:00"
                        else
                            String.format("%02d:%02d", minutes, seconds),
                        color = Color.White,
                        fontSize = 18.sp,
                        lineHeight = 18.sp,
                        fontFamily = Typo.interFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                text = "FLAGS CHALLENGE",
                style = TextStyle(
                    color = theme,
                    fontSize = 22.sp,
                    lineHeight = 22.sp,
                    fontFamily = Typo.interFamily,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.8f),
                        offset = Offset(3f, 3f),
                        blurRadius = 2f
                    )
                )
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(outline)
        )
    }
}