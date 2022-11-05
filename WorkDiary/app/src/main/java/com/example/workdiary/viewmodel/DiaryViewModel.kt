package com.example.workdiary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.workdiary.data.DiaryInfo
import com.example.workdiary.manager.WorkManager
import com.example.workdiary.repository.localsource.Work
import com.example.workdiary.repository.WorkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val repository: WorkRepository
): ViewModel() {

    private val workManager by lazy { WorkManager() }

    val allDiary: LiveData<List<DiaryInfo>> by lazy {
        repository.getDoneWorksAll().map {
            workManager.getDiaryList(it)
        }
    }
}