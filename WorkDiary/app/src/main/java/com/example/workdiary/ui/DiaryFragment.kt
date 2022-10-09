package com.example.workdiary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workdiary.R
import com.example.workdiary.adapter.DiaryAdapter
import com.example.workdiary.data.DiaryInfo
import com.example.workdiary.viewmodel.DiaryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiaryFragment : Fragment() {
    private val diaryViewModel: DiaryViewModel by viewModels()
    lateinit var diaryAdapter: DiaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_diary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewInit(view)
        viewModelInit()
    }

    private fun recyclerViewInit(view:View) {
        // recyclerView 초기화
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_diary_recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        diaryAdapter = DiaryAdapter(listOf()) // empty list adapter
        recyclerView.adapter = diaryAdapter
    }

    private fun viewModelInit() {
        diaryViewModel.getAllDiaryInfo().observe(viewLifecycleOwner, Observer { work_sort_by_date ->
            // List<Work> to List<DiaryInfo>
            val diaryList = mutableListOf<DiaryInfo>()
            var idx=-1
            for (work in work_sort_by_date){
                val cur_year = work.wDate.split("/")[0].toInt()
                val cur_month = work.wDate.split("/")[1].toInt()
                if(idx==-1){
                    // 첫 index
                    diaryList.add(
                        DiaryInfo(
                            cur_year,
                            cur_month,
                            work
                        )
                    )
                    idx++
                } else if(diaryList[idx].year != cur_year || diaryList[idx].month != cur_month) {
                    // year/month 가 변함, 새로운 DiaryInfo 추가
                    diaryList.add(
                        DiaryInfo(
                            cur_year,
                            cur_month,
                            work
                        )
                    )
                    idx++
                } else {
                    // 다음 index의 year/month 가 변하지 않았을 때
                    diaryList[idx].workList.add(work)
                }
            }

            // set recyclerView
            if(diaryList.isNotEmpty()){
                // item 존재
                requireActivity().findViewById<TextView>(R.id.tv_main_comment).text = "아직 완료된 노동일정이 없어요"
                requireActivity().findViewById<TextView>(R.id.tv_main_comment).visibility = View.INVISIBLE
                diaryAdapter.setDiaryList(diaryList)
            } else {
                // item 존재하지 않음
                requireActivity().findViewById<TextView>(R.id.tv_main_comment).text = "아직 완료된 노동일정이 없어요"
                requireActivity().findViewById<TextView>(R.id.tv_main_comment).visibility = View.VISIBLE
            }
        })
    }
}