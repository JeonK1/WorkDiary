package com.example.workdiary.Fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workdiary.Activity.AddWorkActivity
import com.example.workdiary.Adapter.WorkAdapter
import com.example.workdiary.R
import com.example.workdiary.Model.WorkInfo
import com.example.workdiary.SQLite.DBManager
import kotlinx.android.synthetic.main.activity_add_work.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class WorkFragment : Fragment() {

    lateinit var workRecyclerView: RecyclerView
    lateinit var workAdapter: WorkAdapter

    //for dialog
    val DAY_OF_WEEK: ArrayList<String> = arrayListOf("", "일", "월", "화", "수", "목", "금", "토")
    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    lateinit var startTimeSetListener: TimePickerDialog.OnTimeSetListener
    lateinit var endTimeSetListener: TimePickerDialog.OnTimeSetListener
    lateinit var workYear: String
    lateinit var workMonth: String
    lateinit var workDay: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_work, container, false)
        workRecyclerView = root.findViewById(R.id.rv_work_recyclerView)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewInit()
    }

    override fun onResume() {
        super.onResume()
        recyclerViewInit()
    }

    private fun recyclerViewInit() {
        //dummy data
        val dbManager = DBManager(context!!)
        val workList = dbManager.getWorkAll()
        workRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        workAdapter = WorkAdapter(workList)
        workAdapter.itemClickListener = object : WorkAdapter.OnItemClickListener {
            override fun OnItemClick(
                holder: WorkAdapter.MyViewHolder,
                view: View,
                position: Int
            ) {
                if(position == workList.size){
                    //마지막 공간은 노동 추가버튼, 클릭시 Activity 생성
                    val intent = Intent(context, AddWorkActivity::class.java)
                    startActivity(intent)

                    /**
                    // DialogView 생성
                    val mDialogView = LayoutInflater.from(context).inflate(R.layout.activity_add_work, null)
                    val mBuilder = AlertDialog.Builder(context!!).setView(mDialogView)
                    val mAlertDialog = mBuilder.show()
                    mAlertDialog.setCanceledOnTouchOutside(false)
                    mAlertDialog.window!!.setGravity(Gravity.CENTER)

                    // default 값 init
                    val cal = Calendar.getInstance()
                    mDialogView.tv_addwork_mon.text = "%02d".format(cal.get(Calendar.MONTH)+1) + "월"
                    mDialogView.tv_addwork_day.text = "%02d".format(cal.get(Calendar.DAY_OF_MONTH)) + "일"
                    mDialogView.tv_addwork_dayofweek.text = "(" + DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK)] + ")"
                    mDialogView.tv_addwork_startTime.text = "%02d".format(cal.get(Calendar.HOUR)) + ":00"
                    mDialogView.tv_addwork_endTime.text = "%02d".format(cal.get(Calendar.HOUR)+1) + ":00"

                    //listener init
                    mDialogView.act_addwork_title.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(s: Editable?) {
                            // 세트입력 autoCompleteListener에 List 추가
                            val setNameList = dbManager.getSetNameAll(mDialogView.act_addwork_title.text.toString())
                            if(setNameList.size>0) {
                                // title에 적은게 db에 잇는거면 set쪽에 List 적용하기
                                val adapter = ArrayAdapter(
                                    context!!,
                                    android.R.layout.simple_dropdown_item_1line,
                                    setNameList
                                )
                                mDialogView.act_addwork_set.setAdapter(adapter)
                            } else {
                                // title에 적은게 db에 없으며 set쪽에 빈List 적용하기
                                val emptyAdapter = ArrayAdapter(
                                    context!!,
                                    android.R.layout.simple_dropdown_item_1line,
                                    ArrayList<String>()
                                )
                                mDialogView.act_addwork_set.setAdapter(emptyAdapter)
                            }
                        }
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        }
                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        }
                    })

                    //autoCompleteInit
                    val workNameList = dbManager.getWorkNameAll()
                    val adapter = ArrayAdapter(
                        context!!,
                        android.R.layout.simple_dropdown_item_1line,
                        workNameList
                    )
                    mDialogView.act_addwork_title.setAdapter(adapter)

                    //pickerInit
                    dateSetListener =
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                            val cal = Calendar.getInstance()
                            cal.set(Calendar.YEAR, year)
                            cal.set(Calendar.MONTH, monthOfYear)
                            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            workYear = cal.get(Calendar.YEAR).toString()
                            workMonth = (cal.get(Calendar.MONTH) + 1).toString()
                            workDay = cal.get(Calendar.DAY_OF_MONTH).toString()
                            mDialogView.tv_addwork_mon.text = "%02d".format(workMonth.toInt()) + "월"
                            mDialogView.tv_addwork_day.text = "%02d".format(workDay.toInt()) + "일"
                            mDialogView.tv_addwork_dayofweek.text = "(" + DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK)] + ")"
                        }
                    startTimeSetListener =
                        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                            mDialogView.tv_addwork_startTime.text = "%02d".format(hourOfDay)+":"+"%02d".format(minute)
                        }
                    endTimeSetListener =
                        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                            mDialogView.tv_addwork_endTime.text = "%02d".format(hourOfDay)+":"+"%02d".format(minute)
                        }

                    //buttonInit
                    mDialogView.ll_addwork_pickStartTime.setOnClickListener {
                        TimePickerDialog(context!!,
                            android.R.style.Theme_Holo_Light_Dialog,
                            startTimeSetListener,
                            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                            Calendar.getInstance().get(Calendar.MINUTE),
                            android.text.format.DateFormat.is24HourFormat(context!!)
                        ).show()
                    }
                    mDialogView.ll_addwork_pickEndTime.setOnClickListener {
                        TimePickerDialog(context!!,
                            android.R.style.Theme_Holo_Light_Dialog,
                            endTimeSetListener,
                            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                            Calendar.getInstance().get(Calendar.MINUTE),
                            android.text.format.DateFormat.is24HourFormat(context!!)
                        ).show()
                    }
                    mDialogView.ll_addwork_pickDate.setOnClickListener {
                        DatePickerDialog(context!!,
                            dateSetListener,
                            Calendar.getInstance().get(Calendar.YEAR),
                            Calendar.getInstance().get(Calendar.MONTH),
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }
                    mDialogView.btn_addwork_inputLowestMoney.setOnClickListener {
                        mDialogView.et_addwork_money.setText("8590")
                    }
                    mDialogView.tv_addwork_saveBtn.setOnClickListener {
                        val workTitle = mDialogView.act_addwork_title.text.toString()
                        val workSet = mDialogView.act_addwork_set.text.toString()
                        val workDate = "$workYear/${"%02d".format(workMonth.toInt())}/${"%02d".format(workDay.toInt())}"
                        val workStartTime = mDialogView.tv_addwork_startTime.text.toString()
                        val workEndTime = mDialogView.tv_addwork_endTime.text.toString()
                        val workMoney = mDialogView.et_addwork_money.text.toString().toInt()
                        dbManager.addWork(workTitle, workSet, workDate, workStartTime, workEndTime, workMoney)
                        mAlertDialog.dismiss()
                    }
                    mDialogView.ib_addwork_backBtn.setOnClickListener {
                        mAlertDialog.dismiss()
                    }
                    */
                }
            }
        }
        workRecyclerView.adapter = workAdapter
    }

}
