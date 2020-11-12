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
import kotlinx.android.synthetic.main.dialog_box.view.*
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
                }
            }
        }
        workAdapter.itemLongClickListener = object : WorkAdapter.OnLongItemClickListener{
            override fun OnLongItemClick(
                holder: WorkAdapter.MyViewHolder,
                view: View,
                position: Int
            ) {
                // DialogView 생성
                val mDialogView = LayoutInflater.from(this@WorkFragment.context!!).inflate(R.layout.dialog_box, null)
                val mBuilder = AlertDialog.Builder(this@WorkFragment.context!!).setView(mDialogView)
                val mAlertDialog = mBuilder.show()
                mAlertDialog.setCanceledOnTouchOutside(false)
                mAlertDialog.window!!.setGravity(Gravity.CENTER)
                mDialogView.tv_dialog_title.setText("노동일정 제거")
                mDialogView.tv_dialog_context.setText("${holder.date.text.toString()}의 ${holder.title.text.toString()} 노동 일정을 제거할까요??")
                mDialogView.tv_dialog_ok.setText("예")
                mDialogView.tv_dialog_no.setText("아니오")
                mDialogView.tv_dialog_ok.setOnClickListener {
                    // 확인 버튼 누름
                    dbManager.deleteWork(workAdapter.items[position].wId)
                    recyclerViewInit()
                    mAlertDialog.dismiss()
                }
                mDialogView.tv_dialog_no.setOnClickListener{
                    // 취소 버튼 누름
                    mAlertDialog.dismiss()
                }
            }

        }
        workAdapter.okBtnClickListener = object : WorkAdapter.OnOKBtnClickListener{
            override fun OnOkBtnClick(holder: WorkAdapter.MyViewHolder, view: View, position: Int) {
                // DialogView 생성
                val mDialogView = LayoutInflater.from(this@WorkFragment.context!!).inflate(R.layout.dialog_box, null)
                val mBuilder = AlertDialog.Builder(this@WorkFragment.context!!).setView(mDialogView)
                val mAlertDialog = mBuilder.show()
                mAlertDialog.setCanceledOnTouchOutside(false)
                mAlertDialog.window!!.setGravity(Gravity.CENTER)
                mDialogView.tv_dialog_title.setText("노동 완료")
                mDialogView.tv_dialog_context.setText("노동 기록을 일지로 옮길까요??")
                mDialogView.tv_dialog_ok.setText("예")
                mDialogView.tv_dialog_no.setText("아니오")
                mDialogView.tv_dialog_ok.setOnClickListener {
                    // 확인 버튼 누름
                    dbManager.setWorkCheck(workAdapter.items[position].wId)
                    recyclerViewInit()
                    mAlertDialog.dismiss()
                }
                mDialogView.tv_dialog_no.setOnClickListener{
                    // 취소 버튼 누름
                    mAlertDialog.dismiss()
                }
            }
        }
        workRecyclerView.adapter = workAdapter
    }

}
