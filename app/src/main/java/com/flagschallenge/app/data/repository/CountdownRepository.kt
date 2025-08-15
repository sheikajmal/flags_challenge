package com.flagschallenge.app.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import com.flagschallenge.app.presentation.feature.dataStore
import com.flagschallenge.app.utils.Constants.MODE_IDLE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CountdownRepository(private val context: Context) {
    private val MODE_KEY = intPreferencesKey("mode")
    private val TARGET_KEY = longPreferencesKey("scheduled_time")
    private val SEQUENCE_START_KEY = longPreferencesKey("sequence_start_time")
    private val SCORE_KEY = intPreferencesKey("score")

    // mode

    suspend fun saveMode(mode: Int) {
        context.dataStore.edit { prefs ->
            prefs[MODE_KEY] = mode
        }
    }

    // scheduled time

    suspend fun saveScheduledTime(millis: Long) {
        context.dataStore.edit { prefs ->
            prefs[TARGET_KEY] = millis
        }
    }

    suspend fun clearScheduledTime() {
        context.dataStore.edit { prefs ->
            prefs.remove(TARGET_KEY)
        }
    }

    // quiz start time

    suspend fun saveQuizStartTime(millis: Long) {
        context.dataStore.edit { prefs ->
            prefs[SEQUENCE_START_KEY] = millis
        }
    }

    suspend fun clearQuizStartTime() {
        context.dataStore.edit { prefs ->
            prefs.remove(SEQUENCE_START_KEY)
        }
    }

    // score

    suspend fun saveScore(score: Int) {
        context.dataStore.edit { prefs ->
            prefs[SCORE_KEY] = score
        }
    }

    suspend fun clearScore() {
        context.dataStore.edit { prefs ->
            prefs.remove(SCORE_KEY)
        }
    }

    // clear all

    suspend fun clearAll() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    fun modeFlow(): Flow<Int> = context.dataStore.data.map { prefs -> prefs[MODE_KEY] ?: MODE_IDLE }
    fun scheduledTimeFlow(): Flow<Long?> = context.dataStore.data.map { prefs -> prefs[TARGET_KEY] }
    fun sequenceStartFlow(): Flow<Long?> = context.dataStore.data.map { prefs -> prefs[SEQUENCE_START_KEY] }
    fun scoreFlow(): Flow<Int?> = context.dataStore.data.map { prefs -> prefs[SCORE_KEY] }
}