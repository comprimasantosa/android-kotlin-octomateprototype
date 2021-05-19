package com.primasantosa.android.octomateprototype.view.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.primasantosa.android.octomateprototype.data.model.Announcement
import com.primasantosa.android.octomateprototype.databinding.LayoutItemAnnouncementBinding

class ProfileAnnouncementAdapter: ListAdapter<Announcement, ProfileAnnouncementAdapter.ViewHolder>(DiffCallback) {
    object DiffCallback: DiffUtil.ItemCallback<Announcement>() {
        override fun areItemsTheSame(oldItem: Announcement, newItem: Announcement): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Announcement, newItem: Announcement): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder(private val binding: LayoutItemAnnouncementBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(announcement: Announcement) {
            binding.tvTitle.text = announcement.title
            binding.tvSubtitle.text = announcement.subtitle
            binding.tvBody.text = announcement.body
            binding.tvDate.text = announcement.date
            binding.tvTag.text = announcement.tag
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = LayoutItemAnnouncementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}