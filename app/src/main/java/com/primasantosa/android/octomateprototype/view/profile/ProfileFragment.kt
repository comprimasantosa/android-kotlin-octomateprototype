package com.primasantosa.android.octomateprototype.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.primasantosa.android.octomateprototype.R
import com.primasantosa.android.octomateprototype.databinding.FragmentProfileBinding
import com.primasantosa.android.octomateprototype.util.DataUtil
import com.primasantosa.android.octomateprototype.util.PreferencesUtil
import com.primasantosa.android.octomateprototype.util.UIUtil
import com.primasantosa.android.octomateprototype.view.base.MainActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var announcementAdapter: ProfileAnnouncementAdapter
    private lateinit var reminderAdapter: ProfileReminderAdapter
    private lateinit var prefs: PreferencesUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }

    private fun initUI() {
        prefs = PreferencesUtil(requireContext().applicationContext)
        lifecycleScope.launch {
            prefs.getName.collect { name ->
                binding.tvName.text = name
            }
        }
        binding.imgProfile.load(R.drawable.placeholder_image) {
            transformations(CircleCropTransformation())
        }
        binding.btnCheckIn.setOnClickListener {
            goToTimeInSelfie()
        }
        setupToolbar()
        setupAnnouncementList()
        setupReminderList()
    }

    private fun setupToolbar() {
        UIUtil.setToolbarTitle(activity, resources.getString(R.string.label_profile))
        (activity as MainActivity).supportActionBar?.show()
    }

    private fun setupAnnouncementList() {
        announcementAdapter = ProfileAnnouncementAdapter()
        announcementAdapter.submitList(DataUtil.announcementData)
        binding.rvAnnouncements.adapter = announcementAdapter
    }

    private fun setupReminderList() {
        reminderAdapter = ProfileReminderAdapter()
        reminderAdapter.submitList(DataUtil.reminderData)
        binding.rvReminders.adapter = reminderAdapter
    }

    private fun goToTimeInSelfie() {
        val destination = ProfileFragmentDirections.actionProfileFragmentToTimeInSelfieFragment()
        binding.btnCheckIn.findNavController().navigate(destination)
    }
}