package com.flagschallenge.app.presentation.shared_viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flagschallenge.app.R
import com.flagschallenge.app.data.repository.CountdownRepository
import com.flagschallenge.app.domain.model.Questions
import com.flagschallenge.app.utils.Constants.MODE_COUNTDOWN
import com.flagschallenge.app.utils.Constants.MODE_IDLE
import com.flagschallenge.app.utils.Constants.MODE_QUIZ
import com.flagschallenge.app.utils.Constants.MODE_SCORE
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val ticker: Flow<Long>,
    private val countdownRepository: CountdownRepository
) : ViewModel() {

    private val TOTAL_DURATION = 41_000L
    lateinit var questionsObj: Questions

    private val _mode =
        countdownRepository.modeFlow().stateIn(viewModelScope, SharingStarted.Eagerly, MODE_IDLE)
    val mode: StateFlow<Int> = _mode

    private val _scheduledTime = countdownRepository.scheduledTimeFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null as Long?)
    private val _quizStart = countdownRepository.sequenceStartFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null as Long?)

    private val _score = countdownRepository.scoreFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null as Int?)
    val score: StateFlow<Int?> = _score

    val remainingScheduledTimeMillis: StateFlow<Long?> =
        combine(_scheduledTime, ticker) { target, now ->
            if (target == null) null else max(0L, target - now)
        }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val questionIndex: StateFlow<Int> = combine(_quizStart, ticker) { start, now ->
        if (start == null) -1 else ((now - start) / TOTAL_DURATION).toInt()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, -1)

    val remainingQuestionTimeMillis: StateFlow<Long?> =
        combine(_quizStart, ticker) { start, now ->
            if (start == null) null
            else {
                val elapsed = now - start
                val timeIntoCurrentQuestion = elapsed % TOTAL_DURATION
                val remainingMillis = TOTAL_DURATION - timeIntoCurrentQuestion
                remainingMillis
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    init {
        viewModelScope.launch {
            combine(_mode, remainingScheduledTimeMillis) { m, rem -> Pair(m, rem) }
                .collect { (m, rem) ->
                    if (m == MODE_COUNTDOWN && rem != null && rem <= 0L) {
                        startQuiz()
                    }
                }
        }

        viewModelScope.launch {
            questionIndex.collect { idx ->
                if (idx >= 15 && idx != -1) {
                    endQuiz()
                }
            }
        }
    }

    fun readJsonFromAssets(context: Context): Questions {
        val inputStream = context.assets.open("questions.json")
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val json = bufferedReader.use { it.readText() }
        return Gson().fromJson(json, Questions::class.java)
    }

    fun scheduleTime(hours: Long, minutes: Long, seconds: Long) = viewModelScope.launch {
        val totalMillis = hours * 3600_000L + minutes * 60_000L + seconds * 1000L
        val target = System.currentTimeMillis() + totalMillis
        countdownRepository.saveScheduledTime(target)
        countdownRepository.saveMode(MODE_COUNTDOWN)
    }

    fun cancelScheduledTime() = viewModelScope.launch {
        val currentMode = _mode.value
        if (currentMode == MODE_COUNTDOWN) {
            countdownRepository.clearScheduledTime()
            countdownRepository.saveMode(MODE_IDLE)
        }
    }

    private fun startQuiz() = viewModelScope.launch {
        if (_mode.value != MODE_QUIZ) {
            countdownRepository.saveScore(0)
            countdownRepository.saveMode(MODE_QUIZ)
            countdownRepository.saveQuizStartTime(System.currentTimeMillis())
        }
    }

    private fun endQuiz() = viewModelScope.launch {
        countdownRepository.clearQuizStartTime()
        countdownRepository.clearScheduledTime()
        countdownRepository.saveMode(MODE_SCORE)
    }

    fun saveScore() = viewModelScope.launch {
        val totalScore = (_score.value?:0) + 1
        countdownRepository.saveScore(totalScore)
    }

    fun clearScore() = viewModelScope.launch {
        countdownRepository.saveMode(MODE_IDLE)
        countdownRepository.clearScore()
    }

    fun clearAll() {
        viewModelScope.launch { countdownRepository.clearAll() }
    }

    fun getCountryFlag(countryCode: String): Int {
        return when (countryCode) {
            "NZ" -> R.drawable.nz
            "AW" -> R.drawable.aw
            "EC" -> R.drawable.ec
            "PY" -> R.drawable.py
            "KG" -> R.drawable.kg
            "PM" -> R.drawable.pm
            "JP" -> R.drawable.jp
            "TM" -> R.drawable.tm
            "GA" -> R.drawable.ga
            "MQ" -> R.drawable.mq
            "BZ" -> R.drawable.bz
            "CZ" -> R.drawable.cz
            "AE" -> R.drawable.ae
            "JE" -> R.drawable.je
            else -> R.drawable.ls
        }
    }
}