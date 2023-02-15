package com.example.workdiary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
): ViewModel() {
    private val _fragmentStateLiveData = MutableLiveData(FragmentState.WorkFragmentState)
    val fragmentStateLiveData: LiveData<FragmentState> get() = _fragmentStateLiveData

    fun updateFragmentState(state: FragmentState) {
        _fragmentStateLiveData.postValue(state)
    }

    enum class FragmentState {
        WorkFragmentState, DiaryFragmentState
    }
}
