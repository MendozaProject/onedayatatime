package com.example.onedayatatime.db

import androidx.room.*
import java.util.*

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Query("SELECT * FROM task WHERE id = (:id)")
    fun get(id: Long): Task

    @Query("SELECT * FROM task WHERE checked = (:checked)")
    fun getAllChecked(checked: Boolean): List<Task>

    @Query("SELECT * FROM task WHERE date = (:selectDate) ORDER BY rank ASC")
    fun getAllByDate(selectDate: Date): List<Task>

    @Query("SELECT * FROM task WHERE date != (:selectDate) ORDER BY date ASC")
    fun getAllExceptDate(selectDate: Date): List<Task>

    @Insert
    fun insertAll(vararg tasks: Task)

    @Insert
    fun insertNew(tasks: Task): Long

    @Update
    fun updateAll(vararg tasks: Task)

    @Delete
    fun delete(task: Task)
}