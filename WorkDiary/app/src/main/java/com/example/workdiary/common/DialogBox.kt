package com.example.workdiary.common

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import com.example.workdiary.R
import com.example.workdiary.databinding.DialogBoxBinding


class DialogBoxBuilder(
    private val context: Context,
    private val title: String = "",
    private val contents: String = "",
    private val okText: String = "",
    private val noText: String = "",
) {
    private var dialog: AlertDialog? = null
    var okClickListener: ((dialog: AlertDialog?) -> Unit)? = null
    var noClickListener: ((dialog: AlertDialog?) -> Unit)? = null

    fun show(): AlertDialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_box, null)
        val binding = DialogBoxBinding.bind(view).apply {
            tvDialogTitle.text = title
            tvDialogContext.text = contents
            tvDialogOk.text = okText
            tvDialogNo.text = noText
            tvDialogOk.setOnClickListener {
                okClickListener?.invoke(dialog)
            }
            tvDialogNo.setOnClickListener {
                noClickListener?.invoke(dialog)
            }
        }

        return AlertDialog.Builder(context)
            .setView(binding.root)
            .show().apply {
                window!!.setGravity(Gravity.CENTER)
                window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }.also {
                dialog = it
            }
    }
}
