package com.example.workdiary.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import com.example.workdiary.data.DiaryInfo
import com.example.workdiary.manager.WorkManager
import com.example.workdiary.repository.localsource.Work
import com.example.workdiary.repository.localsource.WorkDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WorkRepository @Inject constructor(
    private val workDao: WorkDao
) {
    private val workManager by lazy { WorkManager() }

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

    fun getDiaryInfoAll(): LiveData<List<DiaryInfo>> {
        // DiaryInfo 값 가져오기
        return workDao.getDoneWorksAll().map {
            workManager.getDiaryList(it)
        }
    }

    fun getWorksAll(): LiveData<List<Work>> {
        // WorkAdapter에 넣기 위한 Work 값 가져오기
        return workDao.getWorkInfoAll()
    }


    suspend fun getTitleNames() = withContext(Dispatchers.Default) {
        workDao.getWorkInfoAll().asFlow().map {
            workManager.getTitleNames(it)
        }
    }

    suspend fun getSetNames(title: String) = withContext(Dispatchers.Default) {
        workDao.getWorkInfoAll().asFlow().map {
            workManager.getSetNames(it, title)
        }
    }

    suspend fun getWorks(title: String, setName: String) = withContext(Dispatchers.Default) {
        workDao.getWorkInfoAll().asFlow().map {
            workManager.getWorks(it, title, setName)
        }
    }
}