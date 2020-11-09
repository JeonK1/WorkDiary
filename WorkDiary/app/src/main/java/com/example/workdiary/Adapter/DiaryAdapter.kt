package com.example.workdiary.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workdiary.Model.DiaryInfo
import com.example.workdiary.R

class DiaryAdapter(val items:ArrayList<DiaryInfo>): RecyclerView.Adapter<DiaryAdapter.MyViewHolder>() {
    interface  OnItemClickListener{
        fun OnItemClick(holder: MyViewHolder, view: View, position: Int)
    }

    var itemClickListener: OnItemClickListener?=null

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.tv_itemdiary_title)
        var totalMoney: TextView = itemView.findViewById(R.id.tv_itemdiary_totalMoney)
        var workList: RecyclerView = itemView.findViewById(R.id.rv_itemdiary_workRecyclerView)
        init {
            itemView.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_diary, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = items[position].year.toString() + "년 " + items[position].month + "월 정산 내역"
        holder.workList.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
        holder.workList.adapter =
            WorkForDiaryAdapter(items[position].workList)
        //아 이거 totalMoney 계산해서 ㄱㄱ
        holder.totalMoney.text = "정산값 : " + "3000" + "원"
    }
}