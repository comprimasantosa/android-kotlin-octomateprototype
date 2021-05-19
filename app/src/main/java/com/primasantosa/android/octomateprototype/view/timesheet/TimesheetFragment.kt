package com.primasantosa.android.octomateprototype.view.timesheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.primasantosa.android.octomateprototype.R
import com.primasantosa.android.octomateprototype.databinding.FragmentTimesheetBinding
import com.primasantosa.android.octomateprototype.util.DataUtil
import com.primasantosa.android.octomateprototype.util.UIUtil
import java.text.SimpleDateFormat
import java.util.*

class TimesheetFragment : Fragment() {
    private var _binding: FragmentTimesheetBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var eventAdapter: TimesheetEventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimesheetBinding.inflate(inflater, container, false)

        initUI()

        return binding.root
    }

    private fun initUI() {
        UIUtil.setToolbarTitle(activity, resources.getString(R.string.label_timesheet))
        binding.calView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.ROOT)
            val date = "$year/${month + 1}/$dayOfMonth"
            showSelectedDate(sdf.parse(date)!!)
        }
        showSelectedDate(Date())
        setupEventList()
    }

    private fun showSelectedDate(date: Date) {
        val dateFormat = SimpleDateFormat("EEE dd", Locale.US)
        val monthFormat = SimpleDateFormat("MMMM yyyy", Locale.US)
        binding.tvDate.text = dateFormat.format(date)
        binding.tvMonth.text = monthFormat.format(date)
    }

    private fun setupEventList() {
        eventAdapter = TimesheetEventAdapter()
        eventAdapter.submitList(DataUtil.eventData)
        binding.rvEvent.apply {
            adapter = eventAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
    }
}