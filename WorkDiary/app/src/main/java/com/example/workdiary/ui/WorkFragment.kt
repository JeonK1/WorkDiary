package com.example.workdiary.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.example.workdiary.R
import com.example.workdiary.viewmodel.WorkViewModel
import com.example.workdiary.adapter.WorkAdapter
import com.example.workdiary.databinding.FragmentWorkBinding
import com.example.workdiary.repository.localsource.Work
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_box.view.*

@AndroidEntryPoint
class WorkFragment : Fragment() {
    private val workViewModel: WorkViewModel by viewModels()
    lateinit var workAdapter: WorkAdapter
    private lateinit var binding : FragmentWorkBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvWorkRecyclerView.adapter = WorkAdapter(listOf()).apply {
            onItemClickListener = object : WorkAdapter.OnItemClickListener {
                override fun OnItemClick(holder: WorkAdapter.MyViewHolder, view: View, position: Int) {
                    // item 클릭 시, 버튼 있는 화면 보여주기/숨기기
                    val btnLayout = view.findViewById<LinearLayout>(R.id.ll_itemwork_btnlayout)
                    when (btnLayout.visibility) {
                        View.VISIBLE -> {
                            btnLayout.visibility = View.GONE
                        }
                        View.GONE -> {
                            btnLayout.visibility = View.VISIBLE
                        }
                    }
                }

                override fun OnDeleteBtnClick(
                    holder: WorkAdapter.MyViewHolder, view: View, position: Int
                ) {
                    // item 삭제 버튼 클릭 시, item 제거
                    with(workViewModel) {
                        crateDialog(
                            context = context!!,
                            work = getAllWork().value!![position],
                            action = "DEL",
                            title = "노동일정 제거",
                            contents = "${holder.date.text}의 ${holder.title.text} 노동 일정을 제거할까요??"
                        )
                    }
                }

                override fun OnOkBtnClick(holder: WorkAdapter.MyViewHolder, view: View, position: Int) {
                    // item 확인 버튼 클릭 시, item isDone값 1로 만들기
                    with(workViewModel) {
                        crateDialog(
                            context = context!!,
                            work = getAllWork().value!![position],
                            action = "OK",
                            title = "노동 완료",
                            contents = "노동 기록을 일지로 옮길까요??"
                        )
                    }
                }
            }
        }

        workViewModel.getAllWork().observe(viewLifecycleOwner) { workList ->
            binding.isListEmpty = workList.isEmpty()
            if (workList.isNotEmpty()) {
                // todo : DiffUtil 이용
                workAdapter.setWorks(workList)
            }
        }
    }

    // Todo : 이거 class 로 뺴주고
    fun crateDialog(context: Context, work: Work, action: String, title: String, contents: String) {
        // 다이얼로그 창 띄우기
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_box, null)
        val mBuilder = AlertDialog.Builder(context).setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.window!!.setGravity(Gravity.CENTER)
        mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialogView.tv_dialog_title.text = title
        mDialogView.tv_dialog_context.text = contents
        mDialogView.tv_dialog_ok.text = "예"
        mDialogView.tv_dialog_no.text = "아니오"
        mDialogView.tv_dialog_ok.setOnClickListener {
            // 확인 버튼 누름
            when(action){
                "DEL" -> {
                    // item 삭제하기
                    with(workViewModel) {
                        delete(work)
                    }
                }
                "OK" -> {
                    // item 확인 처리하기
                    with(workViewModel) {
                        setIsDone(work)
                    }
                }
            }
            mAlertDialog.dismiss()
        }
        mDialogView.tv_dialog_no.setOnClickListener{
            // 취소 버튼 누름
            mAlertDialog.dismiss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainActivity.ADD_WORK_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                // AddWorkActivity 에서 새로운 값을 추가함, DB에 추가해주기
                with(workViewModel) {
                    val newWork = data?.getSerializableExtra(AddWorkActivity.ADD_WORK_VALUE) as Work
                    insert(newWork)
                }
            }
        }
    }
}