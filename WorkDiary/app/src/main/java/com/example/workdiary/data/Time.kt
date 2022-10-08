package com.example.workdiary.data

import com.example.workdiary.common.DATE_FORMAT

data class Time(
    val hour: Int,
    val minute: Int
) {
    override fun toString(): String {
        return DATE_FORMAT.format(hour, minute)
    }
}