package com.example.workdiary.data

import com.example.workdiary.common.DATE_FORMAT

data class Date(
    val year: Int,
    val month: Int,
    val day: Int
) {
    override fun toString(): String {
        return DATE_FORMAT.format(year, month, day)
    }
}