package com.example.workdiary.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workdiary.Adapter.DiaryAdapter
import com.example.workdiary.Model.DiaryInfo
import com.example.workdiary.R
import com.example.workdiary.Model.WorkInfo
import com.example.workdiary.SQLite.DBManager

/**
 * A simple [Fragment] subclass.
 */
class DiaryFragment : Fragment() {

    lateinit var diaryRecyclerView: RecyclerView
    lateinit var diaryAdapter: DiaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_work, container, false)
        diaryRecyclerView = root.findViewById(R.id.rv_work_recyclerView)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewInit()
    }

    private fun recyclerViewInit() {
        val dbManager = DBManager(context!!)
        val workList = dbManager.getDiaryAll()

        diaryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        diaryAdapter = DiaryAdapter(workList)
        diaryRecyclerView.adapter = diaryAdapter
    }


}
