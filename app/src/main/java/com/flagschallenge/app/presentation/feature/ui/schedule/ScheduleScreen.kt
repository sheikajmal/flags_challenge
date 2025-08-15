package com.flagschallenge.app.presentation.feature.ui.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flagschallenge.app.presentation.components.ActionBar
import com.flagschallenge.app.presentation.components.DoublePaddingHeightSpacer
import com.flagschallenge.app.presentation.components.NumberPickerDialog
import com.flagschallenge.app.presentation.components.OneHalfPaddingHeightSpacer
import com.flagschallenge.app.presentation.components.SinglePaddingWidthSpacer
import com.flagschallenge.app.presentation.components.TimeBlock
import com.flagschallenge.app.presentation.components.TriplePaddingHeightSpacer
import com.flagschallenge.app.presentation.feature.ui.theme.bgScreen
import com.flagschallenge.app.presentation.feature.ui.theme.onBgScreen
import com.flagschallenge.app.presentation.feature.ui.theme.onOutline
import com.flagschallenge.app.presentation.feature.ui.theme.primary
import com.flagschallenge.app.presentation.feature.ui.theme.theme
import com.flagschallenge.app.presentation.shared_viewmodel.SharedViewModel
import com.flagschallenge.app.utils.Dimen
import com.flagschallenge.app.utils.Typo
import com.flagschallenge.app.utils.offsetTime
import com.flagschallenge.app.utils.showToast

@Composable
fun ScheduleScreen(sharedViewModel: SharedViewModel) {

    val context = LocalContext.current
    var hours by remember { mutableIntStateOf(0) }
    var minutes by remember { mutableIntStateOf(0) }
    var seconds by remember { mutableIntStateOf(0) }
    var pickerType by remember { mutableStateOf<String?>(null) }

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

            OneHalfPaddingHeightSpacer()

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

                DoublePaddingHeightSpacer()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CHALLENGE",
                        color = primary,
                        fontSize = 18.sp,
                        lineHeight = 18.sp,
                        fontFamily = Typo.interFamily,
                        fontWeight = FontWeight.SemiBold
                    )

                    SinglePaddingWidthSpacer()

                    Text(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = primary
                            ),
                        text = "SCHEDULE",
                        color = Color.Black,
                        fontSize = 18.sp,
                        lineHeight = 18.sp,
                        fontFamily = Typo.interFamily,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                TriplePaddingHeightSpacer()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TimeBlock("Hours", hours) { pickerType = "hours" }

                    TimeBlock("Minutes", minutes) { pickerType = "minutes" }

                    TimeBlock("Seconds", seconds) { pickerType = "seconds" }
                }

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
                        String.format(
                            "The time has been set to %02d:%02d:%02d (24-hour format)",
                            hours,
                            minutes,
                            seconds
                        ).showToast(context)
                        val (h, m, s) = offsetTime(hours, minutes, seconds)
                        sharedViewModel.scheduleTime(h, m, s)
                    }
                ) {
                    Text(
                        text = "Save",
                        fontSize = 18.sp,
                        lineHeight = 18.sp,
                        fontFamily = Typo.interFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                DoublePaddingHeightSpacer()
            }
        }
    }


    pickerType?.let { type ->
        NumberPickerDialog(
            title = type.replaceFirstChar { it.uppercase() },
            range = when (type) {
                "hours" -> 0..23
                else -> 0..59
            },
            initialValue = when (type) {
                "hours" -> hours
                "minutes" -> minutes
                else -> seconds
            },
            onValueSelected = { newValue ->
                when (type) {
                    "hours" -> hours = newValue
                    "minutes" -> minutes = newValue
                    "seconds" -> seconds = newValue
                }
                pickerType = null
            },
            onDismiss = { pickerType = null }
        )
    }
}