package com.example.workdiary.data

import com.example.workdiary.repository.localsource.Work

data class DiaryInfo(
    val year: Int,                  // 년
    val month: Int,                 // 월
    val workList: MutableList<Work> // Work List
) {
    constructor(year: Int, month: Int, work: Work) : this(year, month, mutableListOf(work))
}