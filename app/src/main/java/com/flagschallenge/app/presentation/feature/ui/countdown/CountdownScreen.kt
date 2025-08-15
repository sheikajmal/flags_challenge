package com.flagschallenge.app.presentation.feature.ui.countdown

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flagschallenge.app.presentation.components.ActionBar
import com.flagschallenge.app.presentation.components.DoublePaddingHeightSpacer
import com.flagschallenge.app.presentation.components.HalfPaddingHeightSpacer
import com.flagschallenge.app.presentation.feature.ui.theme.bgScreen
import com.flagschallenge.app.presentation.feature.ui.theme.onBgScreen
import com.flagschallenge.app.presentation.feature.ui.theme.onOutline
import com.flagschallenge.app.presentation.feature.ui.theme.onPrimary
import com.flagschallenge.app.presentation.feature.ui.theme.primary
import com.flagschallenge.app.presentation.feature.ui.theme.theme
import com.flagschallenge.app.presentation.shared_viewmodel.SharedViewModel
import com.flagschallenge.app.utils.Dimen
import com.flagschallenge.app.utils.Typo
import com.flagschallenge.app.utils.millisToHms

@SuppressLint("DefaultLocale")
@Composable
fun CountdownScreen(sharedViewModel: SharedViewModel) {

    val remainingScheduledTime by sharedViewModel.remainingScheduledTimeMillis.collectAsState()
    val (h, m, s) = millisToHms(remainingScheduledTime ?: 0L)

    val formattedDisplay = when {
        h > 0 -> "$h hr(s) $m min(s)"
        m >= 0 && (remainingScheduledTime?:0L) > 20_000L -> "${m+1} min(s)"
        else -> {
            val minutesPart = m.toString().padStart(2, '0')
            val secondsPart = s.toString().padStart(2, '0')
            "$minutesPart:$secondsPart"
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(bgScreen)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimen.actionBarSize)
                    .background(theme)
            )

            Column(
                modifier = Modifier
                    .padding(Dimen.halfPadding)
                    .fillMaxWidth()
                    .background(
                        color = onBgScreen.copy(0.3f),
                        shape = RoundedCornerShape(Dimen.themeRadius)
                    )
                    .border(
                        width = 2.dp,
                        color = onOutline,
                        shape = RoundedCornerShape(Dimen.themeRadius)
                    )
            ) {
                ActionBar()

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "WILL START IN",
                        color = primary,
                        fontSize = 24.sp,
                        lineHeight = 24.sp,
                        fontFamily = Typo.interFamily,
                        fontWeight = FontWeight.SemiBold
                    )

                    HalfPaddingHeightSpacer()

                    Text(
                        text = formattedDisplay,
                        color = onPrimary,
                        fontSize = 28.sp,
                        lineHeight = 28.sp,
                        fontFamily = Typo.interFamily,
                        fontWeight = FontWeight.SemiBold
                    )

                    DoublePaddingHeightSpacer()

                    Button(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(100.dp)
                            .height(45.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = theme,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(Dimen.themeRadius),
                        onClick = {
                            sharedViewModel.cancelScheduledTime()
                        }
                    ) {
                        Text(
                            text = "Reset",
                            fontSize = 18.sp,
                            lineHeight = 18.sp,
                            fontFamily = Typo.interFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}