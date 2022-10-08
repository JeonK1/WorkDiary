package com.example.workdiary.di

import android.content.Context
import androidx.room.Room
import com.example.workdiary.data.WorkDao
import com.example.workdiary.data.WorkDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DBModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): WorkDatabase = Room.databaseBuilder(
        context,
        WorkDatabase::class.java,
        "work_database"
    ).build()

    @Singleton
    @Provides
    fun providesWorkDao(database: WorkDatabase): WorkDao = database.workDao()
}