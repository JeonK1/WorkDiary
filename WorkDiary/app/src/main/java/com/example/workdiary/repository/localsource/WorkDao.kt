package com.example.workdiary.repository.localsource

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WorkDao {
    @Insert
    suspend fun insert(work: Work)

    @Update
    suspend fun update(work: Work)

    @Delete
    suspend fun delete(work: Work)

    @Query("select * from Work where wIsDone=0")
    fun getWorkInfoAll(): LiveData<List<Work>>

    @Query("select * from Work where wIsDone=1 order by wDate DESC, wStartTime DESC")
    fun getDiaryInfoAll(): LiveData<List<Work>>

    @Query("select wTitle from Work order by wTitle ASC")
    suspend fun getTitleNames(): List<String>

    @Query("select wSetName from Work where wTitle=:title order by wSetName ASC")
    suspend fun getSetNames(title:String): List<String>

    @Query("select * from Work where wTitle=:title and wSetName=:setName order by wDate DESC, wStartTime DESC")
    suspend fun getWorks(title:String, setName: String): List<Work>

}