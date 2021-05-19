package com.primasantosa.android.octomateprototype.view.base

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.primasantosa.android.octomateprototype.R
import com.primasantosa.android.octomateprototype.databinding.ActivityMainBinding
import com.primasantosa.android.octomateprototype.util.PreferencesUtil
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs: PreferencesUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    fun setToolbarTitle(title: String) {
        binding.toolbarTitle.text = title
    }

    fun hideDrawer() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun showDrawer() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    private fun initUI() {
        prefs = PreferencesUtil(applicationContext)
        lifecycleScope.launch {
            prefs.isLogin.collect { isLogin ->
                if (!isLogin) {
                    goToLogin()
                }
            }
        }
        binding.tvLogout.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            lifecycleScope.launch {
                prefs.setName("")
                prefs.setLogin(false)
                goToLogin()
            }
        }
        setupToolbar()
        setupNavigation()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupNavigation() {
        val navController = getNavController()
        val appBarConfiguration =
            AppBarConfiguration(
                setOf(R.id.profileFragment, R.id.timesheetFragment),
                binding.drawerLayout
            )
        NavigationUI.setupWithNavController(binding.navView, navController)
        NavigationUI.setupWithNavController(binding.toolbar, navController, binding.drawerLayout)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun getNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }

    private fun goToLogin() {
        val navController = getNavController()
        navController.navigate(R.id.loginFragment)
    }
}