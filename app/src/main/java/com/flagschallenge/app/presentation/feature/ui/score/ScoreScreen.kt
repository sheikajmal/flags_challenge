package com.flagschallenge.app.presentation.feature.ui.score

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flagschallenge.app.presentation.components.ActionBar
import com.flagschallenge.app.presentation.components.CustomPaddingHeightSpacer
import com.flagschallenge.app.presentation.components.OneHalfPaddingHeightSpacer
import com.flagschallenge.app.presentation.components.SinglePaddingWidthSpacer
import com.flagschallenge.app.presentation.feature.ui.theme.bgScreen
import com.flagschallenge.app.presentation.feature.ui.theme.onBgScreen
import com.flagschallenge.app.presentation.feature.ui.theme.onOutline
import com.flagschallenge.app.presentation.feature.ui.theme.onPrimary
import com.flagschallenge.app.presentation.feature.ui.theme.theme
import com.flagschallenge.app.presentation.shared_viewmodel.SharedViewModel
import com.flagschallenge.app.utils.Dimen
import com.flagschallenge.app.utils.Typo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ScoreScreen(sharedViewModel: SharedViewModel) {

    val coroutineScope = rememberCoroutineScope()
    val score by sharedViewModel.score.collectAsState()
    var showScore by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            delay(3000)
            showScore = true
            delay(4000)
            sharedViewModel.clearScore()
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

                CustomPaddingHeightSpacer(60.dp)

                if (showScore) {
                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = Dimen.quarterPadding),
                            text = "Score:",
                            color = theme,
                            fontSize = 20.sp,
                            lineHeight = 20.sp,
                            fontFamily = Typo.interFamily,
                            fontWeight = FontWeight.Normal
                        )

                        SinglePaddingWidthSpacer()

                        Text(
                            text = "$score/${sharedViewModel.questionsObj.questions.size}",
                            color = onPrimary,
                            fontSize = 30.sp,
                            lineHeight = 30.sp,
                            fontFamily = Typo.interFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                } else {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Game Over",
                        color = onPrimary,
                        fontSize = 35.sp,
                        lineHeight = 35.sp,
                        fontFamily = Typo.interFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                CustomPaddingHeightSpacer(60.dp)
            }
        }
    }
}