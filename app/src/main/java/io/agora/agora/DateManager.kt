package io.agora.agora

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object DateManager {

    fun getDateFromString(str: String): LocalDate {
        // removing timezone
        return LocalDate.parse(str.substring(0 ,10), DateTimeFormatter.ISO_DATE)
    }

    fun getDateFromNow(): LocalDate {
        return LocalDate.now()
    }

    fun getDateDiff(d1: LocalDate, d2: LocalDate): Long {
        return d1.until(d2, ChronoUnit.DAYS)
    }
}