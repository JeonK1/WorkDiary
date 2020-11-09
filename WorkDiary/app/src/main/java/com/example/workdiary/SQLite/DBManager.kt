package com.example.workdiary.SQLite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.view.View
import com.example.workdiary.Model.WorkInfo
import java.util.ArrayList

/**
 * *** TableName : worktable ***
 * wId integer (primary key, autoincrement)
 * wTitle text
 * wSetName text
 * wDate text
 * wStartTime text
 * wEndTime text
 * wMoney integer
 * wIsDone integer
 */

class DBManager {

    val tableName = "worktable"
    var dbHelper : DBHelper
    var myDatabase : SQLiteDatabase

    constructor(context: Context){
        // 생성자
        dbHelper = DBHelper(context, "db_workdiary.db", null, 1)
        myDatabase = dbHelper.writableDatabase
    }

    fun addWork(title:String, setName:String, date:String, startTime:String, endTime:String, money:Int){
        val contentValues = ContentValues()
        contentValues.put("wTitle", title)
        contentValues.put("wSetName", setName)
        contentValues.put("wDate", date)
        contentValues.put("wStartTime", startTime)
        contentValues.put("wEndTime", endTime)
        contentValues.put("wMoney", money)
        contentValues.put("wIsDone", 0)
        myDatabase.insert("worktable", null, contentValues)
    }

    fun getWorkAll(): ArrayList<WorkInfo> {
        val query = "select * From $tableName where wIsDone=0 order by wDate, wStartTime asc"
        val cursor = myDatabase.rawQuery(query, null)
        cursor.moveToFirst()
        val workList = ArrayList<WorkInfo>()
        while(!cursor.isAfterLast){
            workList.add(
                WorkInfo(
                    cursor.getString(cursor.getColumnIndex("wTitle")),
                    cursor.getString(cursor.getColumnIndex("wDate")),
                    cursor.getString(cursor.getColumnIndex("wStartTime")),
                    cursor.getString(cursor.getColumnIndex("wEndTime")),
                    cursor.getInt(cursor.getColumnIndex("wMoney"))
                )
            )
            cursor.moveToNext()
        }
        return workList
    }

    fun getWorkNameAll(): ArrayList<String> {
        val query = "select wTitle From $tableName group by wTitle order by wTitle asc"
        val cursor = myDatabase.rawQuery(query, null)
        cursor.moveToFirst()
        val workNameList = ArrayList<String>()
        while (!cursor.isAfterLast) {
            workNameList.add(cursor.getString(cursor.getColumnIndex("wTitle")))
            cursor.moveToNext()
        }
        return workNameList
    }

    fun getSetNameAll(title: String): ArrayList<String>{
        Log.e("test", title)
        val query = "select wSetName From $tableName where wTitle=\"$title\" group by wSetName order by wSetName asc"
        val cursor = myDatabase.rawQuery(query, null)
        cursor.moveToFirst()
        val setNameList = ArrayList<String>()
        while (!cursor.isAfterLast) {
            setNameList.add(cursor.getString(cursor.getColumnIndex("wSetName")))
            cursor.moveToNext()
        }
        return setNameList
    }
}