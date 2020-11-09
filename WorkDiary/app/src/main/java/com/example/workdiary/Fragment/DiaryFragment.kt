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
        val workList = dbManager.getWorkAll()
//        val dummyData = ArrayList<WorkInfo>()
//        dummyData.add(
//            WorkInfo(
//                "학원알바",
//                3,
//                "18:00",
//                "19:00",
//                8590
//            )
//        )
//        dummyData.add(
//            WorkInfo(
//                "알바하기싫어",
//                4,
//                "21:00",
//                "23:00",
//                9000
//            )
//        )
//        dummyData.add(
//            WorkInfo(
//                "물류창고",
//                6,
//                "07:00",
//                "12:00",
//                10000
//            )
//        )

        val dummyData2 = ArrayList<DiaryInfo>()
        dummyData2.add(
            DiaryInfo(
                2020,
                8,
                workList
            )
        )

        diaryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        diaryAdapter = DiaryAdapter(dummyData2)
        diaryRecyclerView.adapter = diaryAdapter
    }


}
