package com.example.workdiary.repository

import androidx.lifecycle.LiveData
import com.example.workdiary.repository.localsource.Work
import com.example.workdiary.repository.localsource.WorkDao
import javax.inject.Inject

class WorkRepository @Inject constructor(
    private val workDao: WorkDao
) {
    suspend fun insert(work: Work) {
        // work insert
        workDao.insert(work)
    }

    suspend fun update(work: Work) {
        // work update
        workDao.update(work)
    }

    suspend fun delete(work: Work) {
        // work deleted
        workDao.delete(work)
    }

    fun getDiaryAll(): LiveData<List<Work>> {
        // DiaryInfo에 넣기 쉽도록 Work 값 정렬해서 가져오기
        return workDao.getDiaryInfoAll()
    }

    fun getWorksAll(): LiveData<List<Work>> {
        // WorkAdapter에 넣기 위한 Work 값 가져오기
        return workDao.getWorkInfoAll()
    }

    suspend fun getTitleNames(): List<String> {
        // Work database의 모든 wTitle 목록 오름차순으로 가져오기
        return workDao.getTitleNames()
    }

    suspend fun getSetNames(title:String): List<String> {
        // Work database에서 title이란 제목을 가진 wSetName 목록 오름차순으로 가져오기
        return workDao.getSetNames(title)
    }

    suspend fun getWorks(title:String, setName:String): List<Work> {
        // Work database에서 title과 setName에 해당하는 Work List 최신순으로 가져오기
        return workDao.getWorks(title, setName)
    }
}