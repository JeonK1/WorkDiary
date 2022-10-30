package com.example.workdiary.viewmodel

import android.provider.SyncStateContract.Helpers.update
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workdiary.repository.localsource.Work
import com.example.workdiary.repository.WorkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkViewModel @Inject constructor(
    private val repository: WorkRepository
): ViewModel() {
    val allWorks: LiveData<List<Work>> by lazy {
        repository.getWorksAll()
    }

    fun insert(work: Work) = viewModelScope.launch {
        repository.insert(work)
    }

    fun update(work: Work) = viewModelScope.launch {
        repository.update(work)
    }

    fun setIsDone(work: Work) = viewModelScope.launch {
        update(work.copy(wIsDone = 1))
    }

    fun delete(work: Work) = viewModelScope.launch {
        repository.delete(work)
    }
}