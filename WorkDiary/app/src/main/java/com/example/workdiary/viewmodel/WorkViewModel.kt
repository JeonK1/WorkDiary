package com.example.workdiary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.workdiary.repository.localsource.Work
import com.example.workdiary.repository.WorkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkViewModel @Inject constructor(
    private val repository: WorkRepository
): ViewModel() {
    private val allWorks: LiveData<List<Work>> by lazy {
        repository.getAllWorks()
    }

    fun insert(work: Work) {
        repository.insert(work)
    }

    fun update(work: Work) {
        repository.update(work)
    }

    fun setIsDone(work: Work) {
        work.wIsDone = 1
        update(work)
    }

    fun delete(work: Work) {
        repository.delete(work)
    }

    fun getAllWork(): LiveData<List<Work>> {
        return allWorks
    }


}