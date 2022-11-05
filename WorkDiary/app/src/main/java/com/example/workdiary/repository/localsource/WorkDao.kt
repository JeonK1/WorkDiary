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
    fun getDoneWorksAll(): LiveData<List<Work>>
}