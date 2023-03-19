package com.example.onedayatatime.ui.tasklist

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnKeyListener
import android.view.ViewGroup
import com.example.onedayatatime.R
import com.example.onedayatatime.databinding.ListitemTaskBinding
import com.example.onedayatatime.db.Task
import com.example.onedayatatime.ui.Dates
import com.example.onedayatatime.ui.Strings
import com.example.onedayatatime.ui.recyclerview.ListItem
import java.text.SimpleDateFormat
import java.util.*


abstract class TaskItem (private val task: Task, editing: Boolean) : ListItem<ListitemTaskBinding>() {

    override val type: Int get() = R.id.listitem_task

    private val timeFormat = SimpleDateFormat("hh:mm", Locale.getDefault())

    private val textWatcher = object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(text: Editable?) {
            with (text) {
                if (task.name != toString()) {
                    task.name = toString()
                    onSaveChanges(task)
                }
            }
        }
    }

    private val keyListener = OnKeyListener { p0, keyCode, event ->
        with(binding.taskNameEditText) {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                // Create new task on enter
                if (event != null && event.action == KeyEvent.ACTION_DOWN) {
                    onCreateNewTask(task.rank + 1)
                    clearFocus()
                }
                true
            } else if (keyCode == KeyEvent.KEYCODE_DEL && text.isEmpty()) {
                // Remove task on backspace on empty text
                if (event != null && event.action == KeyEvent.ACTION_DOWN && task.rank != 0) {
                    onDeleteTask(task)
                    deleted = true
                    clearFocus()
                }
                true
            } else {
                false
            }
        }
    }

    var position: Int = -1
    set(value) {
        if (value != field) {
            task.rank = value
            onSaveChanges(task)
        }
        field = value
    }

    private var editing: Boolean = editing
    set(value) {
//        if (!value && field && !deleted){ // Automatically save when closing editing
//            onSaveChanges(task)
//        }

        val editingVisibility = if (value) View.VISIBLE else View.GONE
        with (binding) {
            dividerTop.divider.visibility = editingVisibility
            dividerBottom.divider.visibility = editingVisibility
            clearTextButton.visibility = editingVisibility
        }

        if (value) {
            setTextWatcher(textWatcher)
            setOnKeyListener(keyListener)
            onStartEditing(task)
        } else {
            removeTextWatcher(textWatcher)
            setOnKeyListener(null)
            onExitEditing(task)
        }

        field = value
    }

    private var deleted: Boolean = false

    fun focusView(focus: Boolean) {
        with (binding.taskNameEditText) {
            if (focus) requestFocus()
            else clearFocus()
        }
    }

    override fun bindView(binding: ListitemTaskBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)

        with(binding) {
            with(taskNameEditText) {
                setOnFocusChangeListener { _, focused ->
                    editing = focused
                }
                setText(task.name)
                if (editing) requestFocus()
            }
            setCheckedState(task.checked)
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked != task.checked) {
                    task.checked = isChecked
                    setCheckedState(isChecked)
                    onSaveChanges(task)
                }
            }
            clearTextButton.setOnClickListener {
                taskNameEditText.setText("")
            }
        }
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ListitemTaskBinding {
        return ListitemTaskBinding.inflate(inflater, parent, false)
    }

    fun selectEnd() {
        with (binding.taskNameEditText){ post { setSelection(length()) } }
    }

    private fun setOnKeyListener(keyListener: OnKeyListener?) {
        binding.taskNameEditText.setOnKeyListener(keyListener)
    }

    private fun setTextWatcher(textWatcher: TextWatcher) {
        binding.taskNameEditText.addTextChangedListener(textWatcher)
    }

    private fun removeTextWatcher(textWatcher: TextWatcher) {
        binding.taskNameEditText.addTextChangedListener(textWatcher)
    }

    private fun setCheckedState(checked: Boolean) {
        if (checked) {
            binding.root.alpha = if (!editing) 0.5f else 1.0f
            binding.checkbox.isChecked = true
        } else {
            binding.root.alpha = 1.0f
            binding.checkbox.isChecked = false
        }
    }

    abstract fun onStartEditing(task: Task)
//    abstract fun onClickAddChecklist(task: Task)
//    abstract fun onClickAddDate(task: Task)
//    abstract fun onClickAddNote(task: Task)
    abstract fun onSaveChanges(task: Task)
    abstract fun onCreateNewTask(position: Int)
    abstract fun onDeleteTask(task: Task)
    abstract fun onExitEditing(task: Task)
}