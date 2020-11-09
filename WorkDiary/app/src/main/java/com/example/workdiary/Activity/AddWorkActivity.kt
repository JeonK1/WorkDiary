package com.example.workdiary.Activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.workdiary.R
import com.example.workdiary.SQLite.DBManager
import kotlinx.android.synthetic.main.activity_add_work.*
import java.util.*

class AddWorkActivity : AppCompatActivity() {

    val DAY_OF_WEEK: ArrayList<String> = arrayListOf("", "일", "월", "화", "수", "목", "금", "토")
    lateinit var dateSetListener:DatePickerDialog.OnDateSetListener
    lateinit var startTimeSetListener:TimePickerDialog.OnTimeSetListener
    lateinit var endTimeSetListener:TimePickerDialog.OnTimeSetListener
    lateinit var workYear: String
    lateinit var workMonth: String
    lateinit var workDay: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_work)
        defaultInit()
        listenerInit()
        initAutoCompleteText()
        pickerInit()
        buttonInit()
    }

    private fun defaultInit() {
        val cal = Calendar.getInstance()
        tv_addwork_mon.text = "%02d".format(cal.get(Calendar.MONTH)+1) + "월"
        tv_addwork_day.text = "%02d".format(cal.get(Calendar.DAY_OF_MONTH)) + "일"
        tv_addwork_dayofweek.text = "(" + DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK)] + ")"
        tv_addwork_startTime.text = "%02d".format(cal.get(Calendar.HOUR)) + ":00"
        tv_addwork_endTime.text = "%02d".format(cal.get(Calendar.HOUR)+1) + ":00"
    }

    private fun listenerInit() {
        act_addwork_title.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // 세트입력 autoCompleteListener에 List 추가
                val dbManager = DBManager(applicationContext)
                val setNameList = dbManager.getSetNameAll(act_addwork_title.text.toString())
                if(setNameList.size>0) {
                    // title에 적은게 db에 잇는거면 set쪽에 List 적용하기
                    val adapter = ArrayAdapter(
                        applicationContext,
                        android.R.layout.simple_dropdown_item_1line,
                        setNameList
                    )
                    act_addwork_set.setAdapter(adapter)
                } else {
                    // title에 적은게 db에 없으며 set쪽에 빈List 적용하기
                    val emptyAdapter = ArrayAdapter(
                        applicationContext,
                        android.R.layout.simple_dropdown_item_1line,
                        ArrayList<String>()
                    )
                    act_addwork_set.setAdapter(emptyAdapter)
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun initAutoCompleteText() {
        val dbManager = DBManager(applicationContext)
        val workNameList = dbManager.getWorkNameAll()
        val adapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_dropdown_item_1line,
            workNameList
        )
        act_addwork_title.setAdapter(adapter)
    }

    private fun pickerInit() {
        dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val cal = Calendar.getInstance()
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                workYear = cal.get(Calendar.YEAR).toString()
                workMonth = (cal.get(Calendar.MONTH) + 1).toString()
                workDay = cal.get(Calendar.DAY_OF_MONTH).toString()
                tv_addwork_mon.text = "%02d".format(workMonth.toInt()) + "월"
                tv_addwork_day.text = "%02d".format(workDay.toInt()) + "일"
                tv_addwork_dayofweek.text = "(" + DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK)] + ")"
            }
        startTimeSetListener =
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                tv_addwork_startTime.text = "%02d".format(hourOfDay)+":"+"%02d".format(minute)
            }
        endTimeSetListener =
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                tv_addwork_endTime.text = "%02d".format(hourOfDay)+":"+"%02d".format(minute)
            }
    }

    private fun buttonInit() {
        ll_addwork_pickStartTime.setOnClickListener {
            TimePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog,
                startTimeSetListener,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                android.text.format.DateFormat.is24HourFormat(this)
            ).show()
        }
        ll_addwork_pickEndTime.setOnClickListener {
            TimePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog,
                endTimeSetListener,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                android.text.format.DateFormat.is24HourFormat(this)
            ).show()
        }
        ll_addwork_pickDate.setOnClickListener {
            DatePickerDialog(this,
                dateSetListener,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        btn_addwork_inputLowestMoney.setOnClickListener {
            et_addwork_money.setText("8590")
        }
        tv_addwork_saveBtn.setOnClickListener {
            val dbManager = DBManager(applicationContext)
            val workTitle = act_addwork_title.text.toString()
            val workSet = act_addwork_set.text.toString()
            val workDate = "$workYear/${"%02d".format(workMonth.toInt())}/${"%02d".format(workDay.toInt())}"
            val workStartTime = tv_addwork_startTime.text.toString()
            val workEndTime = tv_addwork_endTime.text.toString()
            val workMoney = et_addwork_money.text.toString().toInt()
            dbManager.addWork(workTitle, workSet, workDate, workStartTime, workEndTime, workMoney)
            finish()
        }
        ib_addwork_backBtn.setOnClickListener {
            finish()
        }
    }
}
