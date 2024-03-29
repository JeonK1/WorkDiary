package com.example.workdiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workdiary.R
import com.example.workdiary.repository.localsource.Work

class DiaryDetailAdapter(val items:List<Work>): RecyclerView.Adapter<DiaryDetailAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.tv_diarydetail_title)
        var workDay: TextView = itemView.findViewById(R.id.tv_diarydetail_day)
        var workTimeNMoney: TextView = itemView.findViewById(R.id.tv_diarydetail_timeNmoney)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_diary_detail, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //노동시간 구하기
        val startTimeStamp = items[position].wStartTime.split(":")[0].toInt()*60 +
                items[position].wStartTime.split(":")[1].toInt()
        val endTimeStamp = items[position].wEndTime.split(":")[0].toInt()*60 +
                items[position].wEndTime.split(":")[1].toInt()
        val workTimeHour = (endTimeStamp-startTimeStamp)/60
        val workTimeMin = if((endTimeStamp-startTimeStamp)%60 >= 30) 30 else 0

        //xml에 적용하기
        holder.title.text = items[position].wTitle
        holder.workDay.text = "${items[position].wDate.split("/")[2]}일"
        holder.workTimeNMoney.text = "${workTimeHour}시간 ${"%02d".format(workTimeMin)}분 x ${items[position].wMoney}원"
    }
}