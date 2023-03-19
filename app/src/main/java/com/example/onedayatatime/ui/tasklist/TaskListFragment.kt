package com.example.onedayatatime.ui.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onedayatatime.databinding.FragmentTaskListBinding
import com.example.onedayatatime.ui.MainActivity
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class TaskListFragment : Fragment() {

    private val viewModel by viewModels<TaskListViewModel>()
    private lateinit var binding: FragmentTaskListBinding
    private val itemAdapter = ItemAdapter<GenericItem>()
    private val adapter =  FastAdapter.with(itemAdapter)

    private lateinit var date: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        date = Date(requireArguments().getLong(ARG_TIME_MILLIS))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with (binding) {
            recyclerview.adapter = adapter
            recyclerview.layoutManager = LinearLayoutManager(context)
            recyclerview.itemAnimator = null
            appbarLayout.elevation = 0.0f
        }
        observe()
        loadData()
    }

    private fun observe() {
        with (viewModel) {
            listItemsLiveData.observe(viewLifecycleOwner) {
                itemAdapter.set(it)
                adapter.notifyDataSetChanged()
            }
            hideKeyboard.observe(viewLifecycleOwner) {
                if (activity is MainActivity)  {
                    (activity as MainActivity).hideKeyboard()
                }
            }
        }
    }

    private fun loadData() {
        viewModel.loadData(date)
    }

    companion object {
        const val ARG_TIME_MILLIS = "date"
    }
}