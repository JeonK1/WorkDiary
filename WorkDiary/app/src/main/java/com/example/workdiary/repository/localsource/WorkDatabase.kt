package com.example.workdiary.repository.localsource

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Work::class], version = 1, exportSchema = false)
abstract class WorkDatabase:RoomDatabase() {
    abstract fun workDao(): WorkDao
}