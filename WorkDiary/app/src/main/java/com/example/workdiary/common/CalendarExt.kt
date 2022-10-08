package com.example.workdiary.common

import java.util.*

val Calendar.Year
    get() = get(Calendar.YEAR)

val Calendar.Month
    get() = get(Calendar.MONTH)

val Calendar.DayOfMonth
    get() = get(Calendar.DAY_OF_MONTH)

val Calendar.DayOfWeek
    get() = get(Calendar.DAY_OF_WEEK)

val Calendar.HourOfDay
    get() = get(Calendar.HOUR_OF_DAY)
