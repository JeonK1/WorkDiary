package com.example.workdiary.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workdiary.R
import com.example.workdiary.common.DATE_FORMAT
import com.example.workdiary.common.extension.DayOfMonth
import com.example.workdiary.common.extension.Month
import com.example.workdiary.common.extension.Year
import com.example.workdiary.databinding.ItemWorkBinding
import com.example.workdiary.repository.localsource.Work
import java.text.SimpleDateFormat
import java.util.*

class WorkAdapter(
    val context: Context,
    var items:List<Work> = listOf()
):RecyclerView.Adapter<WorkAdapter.MyViewHolder>() {
    interface OnItemClickListener{
        fun OnItemClick(holder: MyViewHolder, view:View, position: Int)
        fun OnDeleteBtnClick(holder: MyViewHolder, view:View, position: Int)
        fun OnOkBtnClick(holder: MyViewHolder, view:View, position: Int)
    }

    var onItemClickListener: OnItemClickListener? = null

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var binding = ItemWorkBinding.bind(itemView)

        init {
            binding.apply {
                root.setOnClickListener {
                    onItemClickListener?.OnItemClick(this@MyViewHolder, it, adapterPosition)
                }
                tvItemworkDelete.setOnClickListener {
                    onItemClickListener?.OnDeleteBtnClick(this@MyViewHolder, it, adapterPosition)
                }
                tvItemworkOkBtn.setOnClickListener {
                    onItemClickListener?.OnOkBtnClick(this@MyViewHolder, it, adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_work, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //dday 구하기
        val dateFormat = SimpleDateFormat(context.getString(R.string.date_format))
        val workDateTimeStamp =
            dateFormat.parse(DATE_FORMAT.format(
                items[position].year,
                items[position].month,
                items[position].day
            ))?.time ?: 0
        val currentTimeStamp = with(Calendar.getInstance()) {
            dateFormat.parse(DATE_FORMAT.format(Year, Month, DayOfMonth))
        }?.time ?: 0

        //xml에 적용하기
        holder.binding.apply {
            tvItemworkTitle.text = items[position].wTitle
            tvItemworkSet.text = items[position].wSetName
            tvItemworkDate.text = context.getString(R.string.date_format_md).format(
                items[position].month,
                items[position].day
            )
            tvItemworkDday.text = when {
                // D-day
                currentTimeStamp < workDateTimeStamp -> context.getString(
                    R.string.d_day,
                    (workDateTimeStamp - currentTimeStamp) / (60 * 60 * 24 * 1000)
                )

                // Today
                currentTimeStamp == workDateTimeStamp -> context.getString(R.string.today)

                // 기간 지남
                else -> context.getString(R.string.end_of_date)
            }
            tvItemworkStartNend.text = context.getString(R.string.time_until_format).format(
                items[position].startHour,
                items[position].startMinute,
                items[position].endHour,
                items[position].endMinute
            )
            tvItemworkWorkTime.text = context.getString(R.string.work_time).format(
                items[position].workTimeHour,
                items[position].workTimeMinute
            )
        }
    }

    override fun getItemCount(): Int = items.size

    fun setWorks(works:List<Work>) {
        this.items = works
        notifyDataSetChanged()
    }
}