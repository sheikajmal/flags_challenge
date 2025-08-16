package com.flagschallenge.app.utils

import android.content.Context
import android.widget.Toast
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime

fun String.showToast(context: Context, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(context, this, length).show()
}

fun millisToHms(millis: Long): Triple<Long, Long, Long> {
    var secondsTotal = millis / 1000
    if (secondsTotal < 0) secondsTotal = 0
    val hours = secondsTotal / 3600
    val minutes = (secondsTotal % 3600) / 60
    val seconds = secondsTotal % 60
    return Triple(hours, minutes, seconds)
}

fun offsetTime(hours: Int, minutes: Int, seconds: Int): Triple<Long, Long, Long> {
    val now = LocalDateTime.now()

    // future time selected (today)
    var futureTime = LocalDateTime.of(
        now.toLocalDate(),
        LocalTime.of(hours, minutes, seconds)
    )

    // past time selected (tomorrow)
    if (futureTime.isBefore(now)) {
        futureTime = futureTime.plusDays(1)
    }

    val duration = Duration.between(now, futureTime)
    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60
    val seconds = duration.seconds % 60
    return Triple(hours, minutes, seconds)
}