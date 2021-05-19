package com.primasantosa.android.octomateprototype.view.timesheet

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.core.text.underline
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.primasantosa.android.octomateprototype.R
import com.primasantosa.android.octomateprototype.data.model.Event
import com.primasantosa.android.octomateprototype.databinding.LayoutItemEventBinding

class TimesheetEventAdapter :
    ListAdapter<Event, TimesheetEventAdapter.ViewHolder>(DiffCallback) {
    object DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(private val binding: LayoutItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.tvTitle.text = event.title
            binding.tvTime.text = SpannableStringBuilder().apply {
                bold { append("Time: ") }
                append(event.timeStart)
                append(" TO ")
                append(event.timeEnd)
            }
            binding.tvTotalHours.text = SpannableStringBuilder().apply {
                bold { append("Total Hours: ") }
                append(event.totalHours)
            }
            binding.tvBreak.text = SpannableStringBuilder().apply {
                bold { append("Break: ") }
                append(event.totalBreak)
            }
            binding.tvEdit.text = SpannableStringBuilder().apply {
                underline { append("Edit") }
            }
            binding.tvTag.text = event.tag

            if (event.isEditable) {
                binding.tvEdit.visibility = View.VISIBLE
            } else {
                binding.tvEdit.visibility = View.GONE
            }

            when (event.tag) {
                "Submitted" -> {
                    binding.cvTag.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.submitted_tag))
                }
                "Approved" -> {
                    binding.cvTag.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.approved_tag))
                }
                else -> {
                    binding.cvTag.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.rejected_tag))
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            LayoutItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}