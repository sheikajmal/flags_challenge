package com.flagschallenge.app.di

import android.content.Context
import com.flagschallenge.app.data.repository.CountdownRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTickerFlow(): Flow<Long> = flow {
        while (true) {
            emit(System.currentTimeMillis())
            delay(1000L)
        }
    }

    @Singleton
    @Provides
    fun provideCountdownRepository(@ApplicationContext context: Context): CountdownRepository {
        return CountdownRepository(context)
    }
}