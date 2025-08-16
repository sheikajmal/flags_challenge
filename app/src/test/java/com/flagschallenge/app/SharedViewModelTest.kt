package com.flagschallenge.app

import app.cash.turbine.test
import com.flagschallenge.app.data.repository.CountdownRepository
import com.flagschallenge.app.presentation.shared_viewmodel.SharedViewModel
import com.flagschallenge.app.utils.Constants.MODE_COUNTDOWN
import com.flagschallenge.app.utils.Constants.MODE_IDLE
import com.flagschallenge.app.utils.Constants.MODE_QUIZ
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class SharedViewModelTest {

    private lateinit var fakeTicker: Flow<Long>
    private lateinit var viewModel: SharedViewModel
    private lateinit var countdownRepository: CountdownRepository

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeTicker = flowOf(System.currentTimeMillis())
        countdownRepository = mock(CountdownRepository::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `save scheduled time and mode`() = runTest {
        whenever(countdownRepository.modeFlow()).thenReturn(flowOf(MODE_IDLE))
        whenever(countdownRepository.scheduledTimeFlow()).thenReturn(flowOf(null))
        whenever(countdownRepository.sequenceStartFlow()).thenReturn(flowOf(null))
        whenever(countdownRepository.scoreFlow()).thenReturn(flowOf(null))

        viewModel = SharedViewModel(
            ticker = fakeTicker,
            countdownRepository = countdownRepository
        )

        viewModel.scheduleTime(1, 2, 3)
        verify(countdownRepository).saveScheduledTime(any())
        verify(countdownRepository).saveMode(MODE_COUNTDOWN)
    }

    @Test
    fun `clear scheduled time when in MODE_COUNTDOWN`() = runTest {
        whenever(countdownRepository.modeFlow()).thenReturn(flowOf(MODE_COUNTDOWN))
        whenever(countdownRepository.scheduledTimeFlow()).thenReturn(flowOf(123L))
        whenever(countdownRepository.sequenceStartFlow()).thenReturn(flowOf(null))
        whenever(countdownRepository.scoreFlow()).thenReturn(flowOf(null))

        viewModel = SharedViewModel(
            ticker = fakeTicker,
            countdownRepository = countdownRepository
        )

        viewModel.cancelScheduledTime()
        verify(countdownRepository).clearScheduledTime()
        verify(countdownRepository).saveMode(MODE_IDLE)
    }

    @Test
    fun `save score should increment score`() = runTest {
        whenever(countdownRepository.modeFlow()).thenReturn(flowOf(MODE_QUIZ))
        whenever(countdownRepository.scheduledTimeFlow()).thenReturn(flowOf(null))
        whenever(countdownRepository.sequenceStartFlow()).thenReturn(flowOf(null))
        whenever(countdownRepository.scoreFlow()).thenReturn(flowOf(5))

        viewModel = SharedViewModel(
            ticker = fakeTicker,
            countdownRepository = countdownRepository
        )

        viewModel.saveScore()
        verify(countdownRepository).saveScore(6) // 5 + 1
    }

    @Test
    fun `clear score and change to MODE_IDLE`() = runTest {
        whenever(countdownRepository.modeFlow()).thenReturn(flowOf(MODE_QUIZ))
        whenever(countdownRepository.scheduledTimeFlow()).thenReturn(flowOf(null))
        whenever(countdownRepository.sequenceStartFlow()).thenReturn(flowOf(null))
        whenever(countdownRepository.scoreFlow()).thenReturn(flowOf(5))

        viewModel = SharedViewModel(
            ticker = fakeTicker,
            countdownRepository = countdownRepository
        )

        viewModel.clearScore()
        verify(countdownRepository).saveMode(MODE_IDLE)
        verify(countdownRepository).clearScore()
    }

    @Test
    fun `remaining scheduled time millis`() = runTest {
        whenever(countdownRepository.modeFlow()).thenReturn(flowOf(MODE_COUNTDOWN))
        whenever(countdownRepository.scheduledTimeFlow()).thenReturn(flowOf(123L))
        whenever(countdownRepository.sequenceStartFlow()).thenReturn(flowOf(null))
        whenever(countdownRepository.scoreFlow()).thenReturn(flowOf(null))

        val targetTime = System.currentTimeMillis() + 5000
        whenever(countdownRepository.scheduledTimeFlow()).thenReturn(flowOf(targetTime))

        viewModel = SharedViewModel(
            ticker = flowOf(System.currentTimeMillis()),
            countdownRepository = countdownRepository
        )

        viewModel.remainingScheduledTimeMillis.test {
            val first = awaitItem()
            assertTrue(first!! in 0..5000)
            cancelAndIgnoreRemainingEvents()
        }
    }
}