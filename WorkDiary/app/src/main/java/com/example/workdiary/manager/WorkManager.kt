package com.example.workdiary.manager

import com.example.workdiary.data.DiaryInfo
import com.example.workdiary.repository.localsource.Work

class WorkManager {
    fun getDiaryList(workList: List<Work>): List<DiaryInfo> {
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

    fun getTitleNames(workList: List<Work>) = workList.map { it.wTitle }

    fun getSetNames(workList: List<Work>, title:String) = workList.filter { it.wTitle == title }.map { it.wSetName }

    fun getWorks(workList: List<Work>, title: String, setName: String) = workList.filter { it.wTitle == title && it.wSetName == setName }
}