package com.example.workdiary.common

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.replaceFragment(@IdRes resId:Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().apply {
        addToBackStack(null)
        replace(resId, fragment)
        commit()
    }
}