package com.flagschallenge.app

import androidx.test.core.app.ApplicationProvider
import com.flagschallenge.app.data.repository.CountdownRepository
import com.flagschallenge.app.utils.Constants.MODE_IDLE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeCountdownRepository : CountdownRepository(ApplicationProvider.getApplicationContext()) {

    private val _modeFlow = MutableStateFlow(MODE_IDLE)
    private val _scheduledTimeFlow = MutableStateFlow<Long?>(null)
    private val _sequenceStartFlow = MutableStateFlow<Long?>(null)
    private val _scoreFlow = MutableStateFlow<Int?>(null)

    override suspend fun saveMode(mode: Int) {
        _modeFlow.value = mode
    }

    override suspend fun saveScheduledTime(millis: Long) {
        _scheduledTimeFlow.value = millis
    }

    override suspend fun clearScheduledTime() {
        _scheduledTimeFlow.value = null
    }

    override suspend fun saveQuizStartTime(millis: Long) {
        _sequenceStartFlow.value = millis
    }

    override suspend fun clearQuizStartTime() {
        _sequenceStartFlow.value = null
    }

    override suspend fun saveScore(score: Int) {
        _scoreFlow.value = score
    }

    override suspend fun clearScore() {
        _scoreFlow.value = null
    }

    override suspend fun clearAll() {
        _modeFlow.value = MODE_IDLE
        _scheduledTimeFlow.value = null
        _sequenceStartFlow.value = null
        _scoreFlow.value = null
    }

    override fun modeFlow(): Flow<Int> = _modeFlow
    override fun scheduledTimeFlow(): Flow<Long?> = _scheduledTimeFlow
    override fun sequenceStartFlow(): Flow<Long?> = _sequenceStartFlow
    override fun scoreFlow(): Flow<Int?> = _scoreFlow
}