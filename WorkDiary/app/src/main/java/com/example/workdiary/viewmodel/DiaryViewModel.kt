package com.example.workdiary.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.workdiary.data.Work
import com.example.workdiary.data.WorkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val repository: WorkRepository
): ViewModel() {

    private val allDiary: LiveData<List<Work>> by lazy {
        repository.getDiaryAll()
    }

    fun getAllDiaryInfo(): LiveData<List<Work>> {
        return allDiary
    }
}