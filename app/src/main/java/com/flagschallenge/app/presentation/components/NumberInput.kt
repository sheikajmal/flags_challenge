package com.flagschallenge.app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flagschallenge.app.presentation.feature.ui.theme.onBgScreen
import com.flagschallenge.app.presentation.feature.ui.theme.onPrimary
import com.flagschallenge.app.presentation.feature.ui.theme.primary
import com.flagschallenge.app.utils.Dimen
import com.flagschallenge.app.utils.Typo

@Composable
fun TimeBlock(label: String, value: Int, onClick: () -> Unit) {
    val tens = value / 10
    val ones = value % 10

    Column(
        modifier = Modifier.clickable{ onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            color = primary,
            fontSize = 13.sp,
            lineHeight = 13.sp,
            fontFamily = Typo.interFamily,
            fontWeight = FontWeight.Normal
        )

        HalfPaddingHeightSpacer()

        Row(horizontalArrangement = Arrangement.spacedBy(Dimen.singlePadding)) {
            DigitBox(tens)

            DigitBox(ones)
        }
    }
}

@Composable
fun DigitBox(digit: Int) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(
                color = onBgScreen,
                shape = RoundedCornerShape(Dimen.themeRadius)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = digit.toString(),
            color = onPrimary,
            fontSize = 28.sp,
            lineHeight = 28.sp,
            fontFamily = Typo.interFamily,
            fontWeight = FontWeight.SemiBold
        )
    }
}