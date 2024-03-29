package com.example.workdiary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.workdiary.adapter.DiaryAdapter
import com.example.workdiary.databinding.FragmentDiaryBinding
import com.example.workdiary.viewmodel.DiaryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiaryFragment : Fragment() {

    private val viewModel: DiaryViewModel by viewModels()
    private lateinit var diaryAdapter: DiaryAdapter
    private lateinit var binding : FragmentDiaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiaryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        diaryAdapter = DiaryAdapter(requireContext())

        // recyclerView 초기화
        binding.rvDiaryRecyclerView.adapter = diaryAdapter

        // viewModel observer init
        viewModel.allDiary.observe(viewLifecycleOwner) { diaryList ->
            binding.isListEmpty = diaryList.isEmpty()
            diaryAdapter.setDiaryList(diaryList)
        }
    }
}