package com.example.workdiary.repository.localsource

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.workdiary.common.DATE_FORMAT
import com.example.workdiary.common.DAY_OF_WEEK
import com.example.workdiary.common.extension.DayOfWeek
import com.example.workdiary.data.Date
import com.example.workdiary.data.Time
import java.io.Serializable
import java.util.*

@Entity
data class Work(
    @PrimaryKey(autoGenerate = true) val wId: Int,           // 고유 id
    @ColumnInfo(name = "wTitle") val wTitle: String,         // 노동 이름
    @ColumnInfo(name = "wSetName") val wSetName: String,     // 노동 파트 명
    @ColumnInfo(name = "wDate") val wDate: String,           // 노동하는 날짜
    @ColumnInfo(name = "wStartTime") val wStartTime: String, // 노동 시작 시간
    @ColumnInfo(name = "wEndTime") val wEndTime: String,     // 노동 끝 시간
    @ColumnInfo(name = "wMoney") val wMoney: Int,            // 시급
    @ColumnInfo(name = "wIsDone") val wIsDone: Int           // 완료 여부 (0:false, 1:true)
) : Serializable {
    constructor(
        wTitle: String,
        wSetName: String,
        wDate: Date,
        wStartTime: Time,
        wEndTime: Time,
        wMoney: Int,
        wIsDone: Int
    ) : this(
        0,
        wTitle,
        wSetName,
        Date(wDate.year, wDate.month, wDate.day).toString(),
        Time(wStartTime.hour, wStartTime.minute).toString(),
        Time(wEndTime.hour, wEndTime.minute).toString(),
        wMoney,
        wIsDone
    )

    // parsed by DATE_FORMAT (@see com.example.workdiary.common.Const)
    val year get() = wDate.split("/")[0].toIntOrNull() ?: 0
    val month get() = wDate.split("/")[1].toIntOrNull()?.plus(1) ?: 0
    val day get() = wDate.split("/")[2].toIntOrNull() ?: 0
    val dayOfWeek get() = DAY_OF_WEEK[Calendar.getInstance().apply {
        set(year, month, day)
    }.DayOfWeek]

    // parsed by TIME_FORMAT (@see com.example.workdiary.common.Const)
    val startHour get() = wStartTime.split(":")[0].toIntOrNull() ?: 0
    val startMinute get() = wStartTime.split(":")[1].toIntOrNull() ?: 0
    val endHour get() = wEndTime.split(":")[0].toIntOrNull() ?: 0
    val endMinute get() = wEndTime.split(":")[1].toIntOrNull() ?: 0

    val workTimeHour get() = ((endHour * 60 + endMinute) - (startHour * 60 + startMinute)) / 60
    val workTimeMinute get() = ((endHour * 60 + endMinute) - (startHour * 60 + startMinute)) % 60
}