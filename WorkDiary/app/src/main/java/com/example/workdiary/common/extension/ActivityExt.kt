package com.example.workdiary.common.extension

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.replaceFragment(@IdRes resId:Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().apply {
        replace(resId, fragment)
        commit()
    }
}