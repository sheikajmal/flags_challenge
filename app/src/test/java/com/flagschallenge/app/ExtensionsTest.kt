package com.flagschallenge.app

import com.flagschallenge.app.utils.millisToHms
import com.flagschallenge.app.utils.offsetTime
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class ExtensionsTest {

    @Test
    fun `millisToHms - correct hms`() {
        val (h, m, s) = millisToHms(3_726_500) // 1h 2m 6.5s
        assertEquals(1, h)
        assertEquals(2, m)
        assertEquals(6, s)
    }

    @Test
    fun `millisToHms - handled negative`() {
        val (h, m, s) = millisToHms(-5000)
        assertEquals(0, h)
        assertEquals(0, m)
        assertEquals(0, s)
    }

    @Test
    fun `offsetTime - correct for future time hms (today)`() {
        val now = LocalDateTime.now()
        val target = now.plusHours(1).plusMinutes(30).plusSeconds(59)
        val (h, m, s) = offsetTime(
            target.hour,
            target.minute,
            target.second
        )
        assertEquals(1, h)
        assertEquals(30, m)
        assertEquals(58, s) // 1s less than target
    }

    @Test
    fun `offsetTime - correct for past time hms (tomorrow)`() {
        val now = LocalDateTime.now()
        val past = now.minusHours(1).minusMinutes(30).minusSeconds(59)
        val (h, m, s) = offsetTime(
            past.hour,
            past.minute,
            past.second
        )
        assertEquals(22, h)
        assertEquals(29, m)
        assertEquals(0, s) // 1s less than target
    }
}