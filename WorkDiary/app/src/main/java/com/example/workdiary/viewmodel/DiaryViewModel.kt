package com.example.workdiary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.workdiary.data.DiaryInfo
import com.example.workdiary.repository.WorkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val repository: WorkRepository
) : ViewModel() {
    val allDiary: LiveData<List<DiaryInfo>> by lazy {
        repository.getDiaryInfoAll()
    }
}