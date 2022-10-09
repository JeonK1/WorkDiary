package com.example.workdiary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workdiary.common.*
import com.example.workdiary.data.Date
import com.example.workdiary.data.Time
import com.example.workdiary.repository.localsource.Work
import com.example.workdiary.repository.WorkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddWorkViewModel @Inject constructor(
    private val repository: WorkRepository
): ViewModel() {

    private val _workLiveData = with(Calendar.getInstance()) {
        MutableLiveData(
            // 초기 설정 값
            // date : 현재 년/월/일
            // startTime : 현재 시:00
            // endTime : 현재 시+1:00
            Work(
                wTitle = "",
                wSetName = "",
                wDate = Date(Year, Month, DayOfMonth),
                wStartTime = Time(HourOfDay, 0),
                wEndTime = Time(HourOfDay + 1, 0),
                wMoney = 0,
                wIsDone = 0
            )
        )
    }
    val workLiveData: LiveData<Work> get() = _workLiveData

    fun getTitleNames(): List<String> {
        return repository.getTitleNames()
    }

    fun getSetNames(title:String): List<String> {
        return repository.getSetNames(title)
    }

    fun getWorks(title:String, setName:String): List<Work> {
        return repository.getWorks(title, setName)
    }

    fun updateWork(work: Work) {
        _workLiveData.postValue(work)
    }

    fun updateWorkDate(year: Int, month: Int, dayOfMonth: Int) {
        _workLiveData.apply {
            value?.let { currentWork ->
                postValue(
                    currentWork.copy(
                        wDate = Date(year, month, dayOfMonth).toString()
                    )
                )
            }
        }
    }

    fun updateWorkStartTime(hourOfDay: Int, minute: Int) {
        _workLiveData.apply {
            value?.let { currentWork ->
                postValue(
                    currentWork.copy(
                        wStartTime = Time(hourOfDay, minute).toString()
                    )
                )
            }
        }
    }

    fun updateWorkEndTime(hourOfDay: Int, minute: Int) {
        _workLiveData.apply {
            value?.let { currentWork ->
                postValue(
                    currentWork.copy(
                        wEndTime = Time(hourOfDay, minute).toString()
                    )
                )
            }
        }
    }

    fun addNewWork() {
        workLiveData.value?.let { work ->
            repository.insert(work)
        }
    }
}