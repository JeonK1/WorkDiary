package com.example.workdiary.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.workdiary.common.replaceFragment
import com.example.workdiary.databinding.ActivityMainBinding
import com.example.workdiary.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.activity = this

        viewModel.fragmentStateLiveData.observe(this) { state ->
            replaceFragment(
                resId = binding.flMainFragment.id,
                fragment = when(state) {
                    MainViewModel.FragmentState.WorkFragmentState -> WorkFragment()
                    MainViewModel.FragmentState.DiaryFragmentState -> DiaryFragment()
                }
            )
            binding.state = state.ordinal
        }
    }

    // 노동 버튼 클릭
    fun clickWork() {
        viewModel.updateFragmentState(MainViewModel.FragmentState.WorkFragmentState)
    }

    // 일지 버튼 클릭
    fun clickDiary() {
        viewModel.updateFragmentState(MainViewModel.FragmentState.DiaryFragmentState)
    }

    // 노동 추가 버튼 클릭
    fun clickAddWork() {
        startActivity(Intent(this, AddWorkActivity::class.java))
    }
}