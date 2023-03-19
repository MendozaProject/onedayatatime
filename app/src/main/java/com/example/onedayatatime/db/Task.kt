package com.example.onedayatatime.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "note") var note: String?,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "rank") var rank: Int,
    @ColumnInfo(name = "checked") var checked: Boolean,
    @ColumnInfo(name = "timeEnabled") var timeEnabled: Boolean,
    @ColumnInfo(name = "fk_checklist") var checkListId: Long?
)