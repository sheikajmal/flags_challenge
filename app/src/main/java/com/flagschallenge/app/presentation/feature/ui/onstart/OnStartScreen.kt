package com.flagschallenge.app.presentation.feature.ui.onstart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.flagschallenge.app.domain.model.Questions
import com.flagschallenge.app.presentation.feature.ui.countdown.CountdownScreen
import com.flagschallenge.app.presentation.feature.ui.quiz.QuizScreen
import com.flagschallenge.app.presentation.feature.ui.schedule.ScheduleScreen
import com.flagschallenge.app.presentation.feature.ui.score.ScoreScreen
import com.flagschallenge.app.presentation.shared_viewmodel.SharedViewModel
import com.flagschallenge.app.utils.Constants.MODE_COUNTDOWN
import com.flagschallenge.app.utils.Constants.MODE_IDLE
import com.flagschallenge.app.utils.Constants.MODE_QUIZ
import com.flagschallenge.app.utils.Constants.MODE_SCORE
import com.google.gson.Gson

@Composable
fun OnStartScreen(sharedViewModel: SharedViewModel) {

    val context = LocalContext.current
    val mode by sharedViewModel.mode.collectAsState()
    val json = sharedViewModel.readJsonFromAssets(context)
    sharedViewModel.questionsObj = Gson().fromJson(json, Questions::class.java)

    when (mode) {
        MODE_IDLE -> ScheduleScreen(sharedViewModel)
        MODE_COUNTDOWN -> CountdownScreen(sharedViewModel)
        MODE_QUIZ -> QuizScreen(sharedViewModel)
        MODE_SCORE -> ScoreScreen(sharedViewModel)
        else -> ScheduleScreen(sharedViewModel)
    }
}