package com.example.workdiary.manager

import com.example.workdiary.data.DiaryInfo
import com.example.workdiary.repository.localsource.Work

class WorkManager(
    val workList: List<Work>
) {
    fun getDiaryList(): List<DiaryInfo> {
        val diaryList = mutableListOf<DiaryInfo>()
        var idx = -1
        for (work in workList) {
            val year = work.year
            val month = work.month
            if (idx == -1) {
                // 첫 index
                diaryList.add(
                    DiaryInfo(year, month, work)
                )
                idx++
            } else if (diaryList[idx].year != year || diaryList[idx].month != month) {
                // year/month 가 변함, 새로운 DiaryInfo 추가
                diaryList.add(
                    DiaryInfo(year, month, work)
                )
                idx++
            } else {
                // 다음 index의 year/month 가 변하지 않았을 때
                diaryList[idx].workList.add(work)
            }
        }
        return diaryList
    }
}