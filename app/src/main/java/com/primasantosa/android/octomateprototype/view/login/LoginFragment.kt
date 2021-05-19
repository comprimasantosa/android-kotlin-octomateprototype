package com.primasantosa.android.octomateprototype.view.login

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.text.underline
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.primasantosa.android.octomateprototype.databinding.FragmentLoginBinding
import com.primasantosa.android.octomateprototype.util.PreferencesUtil
import com.primasantosa.android.octomateprototype.util.UIUtil
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: LoginViewModel by viewModel()
    private lateinit var prefs: PreferencesUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        initUI()
        initObserver()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        })
    }

    private fun initUI() {
        UIUtil.hideToolbar(activity)
        UIUtil.hideDrawer(activity)
        prefs = PreferencesUtil(requireContext().applicationContext)
        binding.tvForgotPass.text = SpannableStringBuilder().apply {
            underline { append("Forgot Password?") }
        }
        binding.btnLogin.setOnClickListener {
            UIUtil.hideKeyboard(binding.btnLogin)
            viewModel.login(
                mapOf(
                    "email" to binding.tilUsername.editText?.text.toString(),
                    "password" to binding.tilPassword.editText?.text.toString()
                )
            )
        }
    }

    private fun initObserver() {
        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) {
                binding.pbLogin.visibility = View.VISIBLE
            } else {
                binding.pbLogin.visibility = View.INVISIBLE
            }
        })

        viewModel.name.observe(viewLifecycleOwner, { name ->
            UIUtil.hideKeyboard(binding.btnLogin)
            if (name.isNotEmpty()) {
                lifecycleScope.launch {
                    prefs.setLogin(true)
                    prefs.setName(name)
                    goToProfile()
                }
            }
        })
    }

    private fun goToProfile() {
        val destination = LoginFragmentDirections.actionLoginFragmentToProfileFragment()
        binding.btnLogin.findNavController().navigate(destination)
    }
}