package com.example.workdiary.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.workdiary.R
import com.example.workdiary.data.DiaryInfo
import com.example.workdiary.databinding.ItemDiaryBinding
import com.example.workdiary.repository.localsource.Work
import java.text.DecimalFormat
import kotlin.math.ceil

class DiaryAdapter(
    val context: Context,
    var items:List<DiaryInfo> = listOf()
): RecyclerView.Adapter<DiaryAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemDiaryBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_diary, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            viewItemdiaryBottomLine.isVisible = items.size - 1 != position // 마지막 item 구분선 제거
            tvItemdiaryTitle.text = context.getString(R.string.date_format_ym).format(items[position].year, items[position].month)
            tvItemdiaryTotalMoney.text = context.getString(R.string.total_money).format(getTotalMoneyString(items[position].workList))
            rvItemdiaryWorkRecyclerView.adapter = DiaryDetailAdapter(items[position].workList)
        }
    }

    // totalMoney 계산하기
    private fun getTotalMoneyString(workList: List<Work>): String =
        DecimalFormat(context.getString(R.string.money_decimal_format)).format(
            workList.sumOf { (it.workTimeHour + it.workTimeMinute.toDouble() / 60) * it.wMoney }.toInt()
        )

    fun setDiaryList(diaryList:List<DiaryInfo>) {
        this.items = diaryList
        notifyDataSetChanged()
    }
}