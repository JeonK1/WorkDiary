package com.example.workdiary.repository.localsource

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Work::class], version = 1)
abstract class WorkDatabase:RoomDatabase() {
    abstract fun workDao(): WorkDao
}