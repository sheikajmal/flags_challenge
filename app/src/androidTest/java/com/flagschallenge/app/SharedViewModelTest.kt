package com.flagschallenge.app

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.flagschallenge.app.domain.model.Questions
import com.flagschallenge.app.presentation.shared_viewmodel.SharedViewModel
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.flow.flowOf
import org.junit.Test

class SharedViewModelTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun readJsonFromAssets_success() {
        val viewModel = SharedViewModel(
            ticker = flowOf(System.currentTimeMillis()),
            countdownRepository = FakeCountdownRepository()
        )
        val questions: Questions = viewModel.readJsonFromAssets(context)
        assertNotNull(questions)
        assert(questions.questions.isNotEmpty())
    }
}