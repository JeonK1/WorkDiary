package com.example.workdiary.viewmodel

import androidx.lifecycle.*
import com.example.workdiary.common.extension.DayOfMonth
import com.example.workdiary.common.extension.HourOfDay
import com.example.workdiary.common.extension.Month
import com.example.workdiary.common.extension.Year
import com.example.workdiary.data.Date
import com.example.workdiary.data.Time
import com.example.workdiary.repository.WorkRepository
import com.example.workdiary.repository.localsource.Work
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddWorkViewModel @Inject constructor(
    private val repository: WorkRepository
) : ViewModel() {
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

    suspend fun getTitleNames() = withContext(viewModelScope.coroutineContext) {
        repository.getTitleNames()
    }

    suspend fun getSetNames(title: String) = withContext(viewModelScope.coroutineContext) {
        repository.getSetNames(title)
    }

    suspend fun getWorks(title: String, setName: String) = withContext(viewModelScope.coroutineContext) {
        repository.getWorks(title, setName)
    }

    fun updateWork(work: Work) {
        _workLiveData.postValue(work)
    }

    fun updateWork(
        title: String? = null,
        setName: String? = null,
        date: Date? = null,
        startTime: Time? = null,
        endTime: Time? = null,
        money: Int? = null,
        isDone: Int? = null,
    ) {
        _workLiveData.value?.also { work ->
            _workLiveData.value = work.copy(
                wTitle = title ?: work.wTitle,
                wSetName = setName ?: work.wSetName,
                wDate = date?.toString() ?: work.wDate,
                wStartTime = startTime?.toString() ?: work.wStartTime,
                wEndTime = endTime?.toString() ?: work.wEndTime,
                wMoney = money ?: work.wMoney,
                wIsDone = isDone ?: work.wIsDone
            )
        }
    }

    fun addNewWork() = viewModelScope.launch {
        workLiveData.value?.let { work ->
            repository.insert(work)
        }
    }
}