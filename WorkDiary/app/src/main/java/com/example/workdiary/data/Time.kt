package com.example.workdiary.data

import com.example.workdiary.common.TIME_FORMAT

data class Time(
    val hour: Int,
    val minute: Int
) {
    override fun toString(): String {
        return TIME_FORMAT.format(hour, minute)
    }
}