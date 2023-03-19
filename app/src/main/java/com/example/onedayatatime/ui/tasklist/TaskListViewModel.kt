package com.example.onedayatatime.ui.tasklist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onedayatatime.db.Task
import com.example.onedayatatime.db.TaskDao
import com.example.onedayatatime.ui.Dates
import com.mikepenz.fastadapter.GenericItem
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(private val taskDao: TaskDao) : ViewModel() {

    companion object {
        const val TAG = "TaskListViewModel"
    }

    val listItemsLiveData: MutableLiveData<List<GenericItem>> = MutableLiveData()
    val hideKeyboard: MutableLiveData<Boolean> = MutableLiveData(false)

    fun loadData(date: Date?) {
        if (date != null) {
            listItemsLiveData.value = getListItemsForDate(date)
        }
    }

    private fun getListItemsForDate(date: Date): List<GenericItem> {
        val items = ArrayList<GenericItem>()
        val tasks = taskDao.getAllByDate(date)

        // Insert Tasks
        tasks.forEach {
            val item = buildNewTaskItem(it, editing = false)
            items.add(item)
        }

        // Add Task Button at the end
        items.add(object : AddTaskItem() {
            override fun onClick() {
                addNewTaskItem(items.size - 1)
            }
        })

        return items
    }

    private fun addNewTaskItem(position: Int) {
        val listItems: MutableList<GenericItem> = listItemsLiveData.value as MutableList<GenericItem>

        val task = Task(
            id = 0,
            name = "",
            note = "",
            date = Dates.dateToSqlDate(Date()),
            rank = position,
            checked = false,
            timeEnabled = false,
            checkListId = null
        )

        task.id = taskDao.insertNew(task)

        Log.d(TAG, "addNewTaskItem: $task")

        listItems.add(position, buildNewTaskItem(task,true))
        offsetPositions(position + 1, listItems)

        listItemsLiveData.value = listItems
    }

    private fun offsetPositions(position: Int, listItems: List<GenericItem>) {
        // Offset all the following items' positions
        listItems.forEachIndexed { i, item ->
            if (item is TaskItem) {
                item.position = i
            }
        }
    }

    private fun buildNewTaskItem(task: Task, editing: Boolean): TaskItem {
         return object : TaskItem(task, editing) {
             override fun onStartEditing(task: Task) { onEditTask(task) }
//             override fun onClickAddChecklist(task: Task) { showCheckListSelector() }
//             override fun onClickAddDate(task: Task) { showDateSelector(Dates.sqlDateToDate(task.date)!!) }
//             override fun onClickAddNote(task: Task) { showNoteSelector() }
             override fun onSaveChanges(task: Task) { saveTask(task) }
             override fun onCreateNewTask(position: Int) { addNewTaskItem(position) }
             override fun onDeleteTask(task: Task) { deleteTask(task) }
             override fun onExitEditing(task: Task) {
//                 textWatcher.task = null
//                 this.removeTextWatcher(textWatcher)
//                 hideKeyboard()
             }
        }
    }

    private fun onEditTask(task: Task) {
    }

    private fun saveTask(task: Task) {
        taskDao.updateAll(task)
        Log.d(TAG, "saveTask: $task")
    }

    private fun deleteTask(task: Task) {
        val listItems = listItemsLiveData.value as MutableList

        if (listItems.size > 1) {
            taskDao.delete(task)
            val position = task.rank
            listItems.removeAt(position)
            offsetPositions(position, listItems)

            val item = listItems[position - 1]
            if (item is TaskItem) {
                item.focusView(true)
                item.selectEnd()
            }
            Log.d(TAG, "deleteTask: $task")
        }
        listItemsLiveData.value = listItems
    }

//    private fun showNoteSelector() {
//        // TODO
//    }
//
//    private fun showDateSelector(date: Date) {
//        // TODO
//    }
//
//    private fun showCheckListSelector() {
//        // TODO
//    }

    private fun hideKeyboard() {
        hideKeyboard.value = !hideKeyboard.value!!
    }
}