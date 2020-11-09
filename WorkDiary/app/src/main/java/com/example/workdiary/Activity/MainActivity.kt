package com.example.workdiary.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.workdiary.Fragment.DiaryFragment
import com.example.workdiary.R
import com.example.workdiary.Fragment.WorkFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val diaryFragment = DiaryFragment()
    val workFragment = WorkFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentInit()
        buttonInit()
    }

    private fun buttonInit() {
        tv_main_diaryBtn.setOnClickListener {
            val fragment = supportFragmentManager.beginTransaction()
            fragment.addToBackStack(null)
            fragment.replace(R.id.fl_main_fragment, diaryFragment)
            fragment.commit()
        }
        tv_main_workBtn.setOnClickListener {
            val fragment = supportFragmentManager.beginTransaction()
            fragment.addToBackStack(null)
            fragment.replace(R.id.fl_main_fragment, workFragment)
            fragment.commit()
        }
    }

    private fun fragmentInit() {
        val fragment = supportFragmentManager.beginTransaction()
        fragment.addToBackStack(null)
        fragment.replace(R.id.fl_main_fragment, workFragment)
        fragment.commit()
    }
}
