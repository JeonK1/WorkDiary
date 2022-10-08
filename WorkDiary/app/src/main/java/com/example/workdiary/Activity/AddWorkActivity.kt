package com.example.workdiary.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.example.workdiary.R
import com.example.workdiary.common.AfterTextChangedListener
import com.example.workdiary.common.DAY_OF_WEEK
import com.example.workdiary.common.LOWEST_MONEY
import com.example.workdiary.databinding.ActivityAddWorkBinding
import com.example.workdiary.databinding.ActivityMainBinding
import com.example.workdiary.viewmodel.AddWorkViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_work.*
import kotlinx.android.synthetic.main.activity_add_work.view.*
import java.util.*
import kotlin.math.min

@AndroidEntryPoint
class AddWorkActivity : AppCompatActivity() {
    companion object {
        const val ADD_WORK_VALUE = "NEW_WORK"
    }

    private val addWorkViewModel: AddWorkViewModel by viewModels()
    private lateinit var binding: ActivityAddWorkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWorkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.activity = this

        addWorkViewModel.workLiveData.observe(this) { work ->
            binding.work = work
        }

        binding.apply {
            // title 관련 AutoCOmpleteTextView에 모든 title stringList 적용
            actAddworkTitle.setAdapter(
                ArrayAdapter(
                    applicationContext,
                    android.R.layout.simple_dropdown_item_1line,
                    addWorkViewModel.getTitleNames()
                )
            )

            // title이 변하였을 때, 해당 title에 해당하는 setName 가져와서 set 관련 AutoCompleteTextView에 적용
            actAddworkTitle.addTextChangedListener(object : AfterTextChangedListener {
                override fun afterTextChanged(s: Editable?) {
                    val wTitle = s.toString()
                    actAddworkSet.setAdapter(
                        ArrayAdapter(
                            applicationContext,
                            android.R.layout.simple_dropdown_item_1line,
                            addWorkViewModel.getSetNames(wTitle)
                        )
                    )
                }
            })

            // title과 setName에 해당하는 Work 값이 존재하면, 해당 내용을 startTime, endTime, money 에 적용하기
            actAddworkSet.addTextChangedListener(object : AfterTextChangedListener {
                override fun afterTextChanged(s: Editable?) {
                    addWorkViewModel.getWorks(
                        title = actAddworkTitle.text.toString(),
                        setName = s.toString()
                    ).getOrNull(0)?.let { work ->
                        addWorkViewModel.updateWork(work)
                    }
                }
            })
        }
    }


    // 날짜 설정
    fun clickSetDate() {
        addWorkViewModel.workLiveData.value?.let { work ->
            if (work.year != null && work.month != null && work.day != null) {
                DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        addWorkViewModel.updateWorkDate(year, month, dayOfMonth)
                    },
                    work.year!!,
                    work.month!!,
                    work.day!!,
                ).show()
            }
        }
    }

    // 시작시간
    fun clickSetStartTime() {
        addWorkViewModel.workLiveData.value?.let { work ->
            if (work.startHour != null && work.startMinute != null) {
                TimePickerDialog(
                    this,
                    android.R.style.Theme_Holo_Light_Dialog,
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        addWorkViewModel.updateWorkStartTime(hourOfDay, minute)
                    },
                    work.startHour!!,
                    work.startMinute!!,
                    android.text.format.DateFormat.is24HourFormat(this)
                ).show()
            }
        }
    }

    // 끝시간
    fun clickSetEndTime() {
        addWorkViewModel.workLiveData.value?.let { work ->
            if (work.startHour != null && work.startMinute != null) {
                TimePickerDialog(
                    this,
                    android.R.style.Theme_Holo_Light_Dialog,
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        addWorkViewModel.updateWorkEndTime(hourOfDay, minute)
                    },
                    work.endHour!!,
                    work.endMinute!!,
                    android.text.format.DateFormat.is24HourFormat(this)
                ).show()
            }
        }
    }

    // 최저시급
    fun clickLowestMoney() {
        binding.etAddworkMoney.setText(LOWEST_MONEY.toString())
    }

    // 저장하기
    fun clickSave() {
        addWorkViewModel.addNewWork()
        setResult(Activity.RESULT_OK)
        finish()
    }

    // 뒤로가기
    fun clickBack() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}