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

    fun getDoneWorksAll(): LiveData<List<Work>> {
        // DiaryInfo에 넣기 쉽도록 Work 값 정렬해서 가져오기
        return workDao.getDoneWorksAll()
    }

    fun getWorksAll(): LiveData<List<Work>> {
        // WorkAdapter에 넣기 위한 Work 값 가져오기
        return workDao.getWorkInfoAll()
    }
}