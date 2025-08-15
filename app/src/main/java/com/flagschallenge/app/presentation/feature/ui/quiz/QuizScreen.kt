package com.flagschallenge.app.presentation.feature.ui.quiz

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flagschallenge.app.presentation.components.ActionBar
import com.flagschallenge.app.presentation.components.DoublePaddingHeightSpacer
import com.flagschallenge.app.presentation.components.OneHalfPaddingHeightSpacer
import com.flagschallenge.app.presentation.feature.ui.theme.bgScreen
import com.flagschallenge.app.presentation.feature.ui.theme.onBgScreen
import com.flagschallenge.app.presentation.feature.ui.theme.onOutline
import com.flagschallenge.app.presentation.feature.ui.theme.onPrimary
import com.flagschallenge.app.presentation.feature.ui.theme.onTheme
import com.flagschallenge.app.presentation.feature.ui.theme.primary
import com.flagschallenge.app.presentation.feature.ui.theme.theme
import com.flagschallenge.app.presentation.shared_viewmodel.SharedViewModel
import com.flagschallenge.app.utils.Dimen
import com.flagschallenge.app.utils.Typo
import com.flagschallenge.app.utils.millisToHms

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("DefaultLocale")
@Composable
fun QuizScreen(sharedViewModel: SharedViewModel) {

    val index by sharedViewModel.questionIndex.collectAsState()
    val remainingScreenTime by sharedViewModel.remainingQuestionTimeMillis.collectAsState()
    var remainingQuestionTime by remember { mutableLongStateOf(0L) }
    val (h, m, s) = millisToHms(if (remainingQuestionTime <= 0L) remainingScreenTime ?: 0L else remainingQuestionTime)
    var selectionId by remember { mutableIntStateOf(0) }

    if (index < 0) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Preparing...",
                color = theme,
                fontSize = 20.sp,
                lineHeight = 20.sp,
                fontFamily = Typo.interFamily,
                fontWeight = FontWeight.SemiBold
            )
        }
        return
    }

    if (index >= 15) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Finishing...",
                color = theme,
                fontSize = 20.sp,
                lineHeight = 20.sp,
                fontFamily = Typo.interFamily,
                fontWeight = FontWeight.SemiBold
            )
        }
        return
    }

    val curQues = sharedViewModel.questionsObj.questions[index]

    LaunchedEffect(index) {
        selectionId = -1
    }

    LaunchedEffect(remainingScreenTime) {
        remainingQuestionTime = (remainingScreenTime ?: 0L) - 11_000L
        if (remainingQuestionTime <= 0L && selectionId == curQues.answer_id)
            sharedViewModel.saveScore()
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
                ActionBar(m, s)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Box(
                        modifier = Modifier
                            .width(43.dp)
                            .height(34.dp)
                            .background(
                                color = Color.Black,
                                shape = RoundedCornerShape(
                                    topEnd = Dimen.themeRadius,
                                    bottomEnd = Dimen.themeRadius
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    color = theme,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${index + 1}",
                                color = Color.White,
                                fontSize = 14.sp,
                                lineHeight = 14.sp,
                                fontFamily = Typo.interFamily,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "GUESS THE COUNTRY FROM THE FRAG?",
                        color = primary,
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        fontFamily = Typo.interFamily,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }

                DoublePaddingHeightSpacer()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .width(100.dp)
                            .height(70.dp),
                        painter = painterResource(sharedViewModel.getCountryFlag(curQues.country_code)),
                        contentDescription = "country_flag"
                    )

                    FlowRow {
                        curQues.countries.forEach { country ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(Dimen.singlePadding)
                                        .width(100.dp)
                                        .height(40.dp)
                                        .then(
                                            if (selectionId == country.id)
                                                Modifier.background(
                                                    color = theme,
                                                    shape = RoundedCornerShape(Dimen.themeRadius)
                                                )
                                            else
                                                Modifier.border(
                                                    width = 1.dp,
                                                    color = if (remainingQuestionTime <= 0L && country.id == curQues.answer_id)
                                                        theme
                                                    else
                                                        onPrimary,
                                                    shape = RoundedCornerShape(Dimen.themeRadius)
                                                )
                                        )
                                        .then(
                                            if (remainingQuestionTime <= 0L)
                                                Modifier
                                            else
                                                Modifier.clickable {
                                                    selectionId = country.id
                                                }
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = country.country_name,
                                        color = if (selectionId == country.id)
                                            Color.White
                                        else
                                            onPrimary,
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp,
                                        fontFamily = Typo.interFamily,
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Center
                                    )
                                }

                                Text(
                                    text = if (remainingQuestionTime <= 0L && country.id == curQues.answer_id)
                                        "Correct"
                                    else if (remainingQuestionTime <= 0L && country.id == selectionId && selectionId != curQues.answer_id)
                                        "Wrong"
                                    else
                                        "",
                                    color = if (country.id == curQues.answer_id) onTheme else theme,
                                    fontSize = 12.sp,
                                    lineHeight = 12.sp,
                                    fontFamily = Typo.interFamily,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                DoublePaddingHeightSpacer()
            }
        }
    }
}
