package com.example.workdiary.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import com.example.workdiary.R
import com.example.workdiary.viewmodel.WorkViewModel
import com.example.workdiary.adapter.WorkAdapter
import com.example.workdiary.common.DialogBoxBuilder
import com.example.workdiary.databinding.FragmentWorkBinding
import com.example.workdiary.repository.localsource.Work
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkFragment : Fragment() {
    private val workViewModel: WorkViewModel by viewModels()
    private lateinit var workAdapter: WorkAdapter
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

                override fun OnDeleteBtnClick(holder: WorkAdapter.MyViewHolder, view: View, position: Int) {
                    // item 삭제 버튼 클릭 시, item 제거
                    context?.also { context ->
                        DialogBoxBuilder(
                            context = context,
                            title = getString(R.string.deleteWorkTitle),
                            contents = getString(R.string.deleteWorkContents, holder.date.text, holder.title.text),
                            okText = getString(R.string.ok),
                            noText = getString(R.string.no)
                        ).apply {
                            okClickListener = { dialog ->
                                workViewModel.getAllWork().value?.get(position)?.also { work ->
                                    workViewModel.delete(work)
                                }
                                dialog?.dismiss()
                            }
                            noClickListener = { dialog ->
                                dialog?.dismiss()
                            }
                        }.show()
                    }
                }

                override fun OnOkBtnClick(holder: WorkAdapter.MyViewHolder, view: View, position: Int) {
                    // item 확인 버튼 클릭 시, item isDone값 1로 만들기
                    context?.also { context ->
                        DialogBoxBuilder(
                            context = context,
                            title = getString(R.string.doneWorkTitle),
                            contents = getString(R.string.doneWorkContents),
                            okText = getString(R.string.ok),
                            noText = getString(R.string.no)
                        ).apply {
                            okClickListener = { dialog ->
                                workViewModel.getAllWork().value?.get(position)?.also { work ->
                                    workViewModel.setIsDone(work)
                                }
                                dialog?.dismiss()
                            }
                            noClickListener = { dialog ->
                                dialog?.dismiss()
                            }
                        }.show()
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