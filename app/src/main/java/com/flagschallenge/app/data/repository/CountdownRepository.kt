package com.flagschallenge.app.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import com.flagschallenge.app.presentation.feature.dataStore
import com.flagschallenge.app.utils.Constants.MODE_IDLE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

open class CountdownRepository(private val context: Context) {
    private val MODE_KEY = intPreferencesKey("mode")
    private val TARGET_KEY = longPreferencesKey("scheduled_time")
    private val SEQUENCE_START_KEY = longPreferencesKey("sequence_start_time")
    private val SCORE_KEY = intPreferencesKey("score")

    // mode

    open suspend fun saveMode(mode: Int) {
        context.dataStore.edit { prefs ->
            prefs[MODE_KEY] = mode
        }
    }

    // scheduled time

    open suspend fun saveScheduledTime(millis: Long) {
        context.dataStore.edit { prefs ->
            prefs[TARGET_KEY] = millis
        }
    }

    open suspend fun clearScheduledTime() {
        context.dataStore.edit { prefs ->
            prefs.remove(TARGET_KEY)
        }
    }

    // quiz start time

    open suspend fun saveQuizStartTime(millis: Long) {
        context.dataStore.edit { prefs ->
            prefs[SEQUENCE_START_KEY] = millis
        }
    }

    open suspend fun clearQuizStartTime() {
        context.dataStore.edit { prefs ->
            prefs.remove(SEQUENCE_START_KEY)
        }
    }

    // score

    open suspend fun saveScore(score: Int) {
        context.dataStore.edit { prefs ->
            prefs[SCORE_KEY] = score
        }
    }

    open suspend fun clearScore() {
        context.dataStore.edit { prefs ->
            prefs.remove(SCORE_KEY)
        }
    }

    // clear all

    open suspend fun clearAll() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    open fun modeFlow(): Flow<Int> = context.dataStore.data.map { prefs -> prefs[MODE_KEY] ?: MODE_IDLE }
    open fun scheduledTimeFlow(): Flow<Long?> = context.dataStore.data.map { prefs -> prefs[TARGET_KEY] }
    open fun sequenceStartFlow(): Flow<Long?> = context.dataStore.data.map { prefs -> prefs[SEQUENCE_START_KEY] }
    open fun scoreFlow(): Flow<Int?> = context.dataStore.data.map { prefs -> prefs[SCORE_KEY] }
}