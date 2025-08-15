package com.flagschallenge.app.presentation.feature

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.flagschallenge.app.presentation.feature.ui.onstart.OnStartScreen
import com.flagschallenge.app.presentation.feature.ui.theme.FlagsChallengeTheme
import com.flagschallenge.app.presentation.shared_viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

val Context.dataStore by preferencesDataStore(name = "settings")

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            FlagsChallengeTheme {
                OnStartScreen(sharedViewModel)
            }
        }
    }
}