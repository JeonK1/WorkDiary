package com.example.workdiary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.workdiary.R
import com.example.workdiary.viewmodel.WorkViewModel
import com.example.workdiary.adapter.WorkAdapter
import com.example.workdiary.common.DialogBoxBuilder
import com.example.workdiary.databinding.FragmentWorkBinding
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
        // adapter 초기화
        workAdapter = WorkAdapter(requireContext()).apply {
            onItemClickListener = object : WorkAdapter.OnItemClickListener {
                override fun OnItemClick(holder: WorkAdapter.MyViewHolder, view: View, position: Int) {
                    // item 클릭 시, 버튼 있는 화면 보여주기/숨기기
                    holder.binding.llItemworkBtnlayout.apply {
                        isVisible = !isVisible
                    }
                }

                override fun OnDeleteBtnClick(holder: WorkAdapter.MyViewHolder, view: View, position: Int) {
                    // item 삭제 버튼 클릭 시, item 제거
                    DialogBoxBuilder(
                        context = context,
                        title = getString(R.string.deleteWorkTitle),
                        contents = getString(R.string.deleteWorkContents, holder.binding.tvItemworkDate.text, holder.binding.tvItemworkTitle.text),
                        okText = getString(R.string.ok),
                        noText = getString(R.string.no)
                    ).apply {
                        okClickListener = { dialog ->
                            workViewModel.allWorks.value?.get(position)?.also { work ->
                                workViewModel.delete(work)
                            }
                            dialog?.dismiss()
                        }
                        noClickListener = { dialog ->
                            dialog?.dismiss()
                        }
                    }.show()
                }

                override fun OnOkBtnClick(holder: WorkAdapter.MyViewHolder, view: View, position: Int) {
                    // item 확인 버튼 클릭 시, item isDone값 1로 만들기
                    DialogBoxBuilder(
                        context = context,
                        title = getString(R.string.doneWorkTitle),
                        contents = getString(R.string.doneWorkContents),
                        okText = getString(R.string.ok),
                        noText = getString(R.string.no)
                    ).apply {
                        okClickListener = { dialog ->
                            workViewModel.allWorks.value?.get(position)?.also { work ->
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

        // recyclerView 초기화
        binding.rvWorkRecyclerView.adapter = workAdapter

        // viewModel observer init
        workViewModel.allWorks.observe(viewLifecycleOwner) { workList ->
            binding.isListEmpty = workList.isEmpty()
            workAdapter.submitList(workList)
        }
    }
}