package com.primasantosa.android.octomateprototype.view.profile

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.primasantosa.android.octomateprototype.data.model.Reminder
import com.primasantosa.android.octomateprototype.databinding.LayoutItemReminderBinding

class ProfileReminderAdapter :
    ListAdapter<Reminder, ProfileReminderAdapter.ViewHolder>(DiffCallback) {
    object DiffCallback : DiffUtil.ItemCallback<Reminder>() {
        override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder(private val binding: LayoutItemReminderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reminder: Reminder) {
            binding.tvTitle.text = reminder.title
            binding.tvPlacement.text = StringBuilder().apply {
                append("Placement: ")
                append(reminder.placement)
            }
            binding.tvFrom.text = SpannableStringBuilder().apply {
                append("From ")
                bold {  append(reminder.dateFrom) }
                append(" To ")
                bold { append(reminder.dateTo) }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            LayoutItemReminderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}