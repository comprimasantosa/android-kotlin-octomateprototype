package com.primasantosa.android.octomateprototype.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.primasantosa.android.octomateprototype.R
import com.primasantosa.android.octomateprototype.view.base.MainActivity
import java.text.SimpleDateFormat
import java.util.*


object UIUtil {
    private lateinit var alertDialogBuilder: AlertDialog.Builder
    private lateinit var alertDialog: AlertDialog
    private lateinit var dateFormat: SimpleDateFormat

    fun setToolbarTitle(activity: FragmentActivity?, title: String) {
        (activity as MainActivity).setToolbarTitle(title)
    }

    fun hideToolbar(activity: FragmentActivity?) {
        (activity as MainActivity).supportActionBar?.hide()
    }

    fun hideDrawer(activity: FragmentActivity?) {
        (activity as MainActivity).hideDrawer()
    }

    fun showDialogTimeIn(context: Context, navigation: () -> Unit) {
        alertDialogBuilder = AlertDialog.Builder(context)
        dateFormat = SimpleDateFormat("HH:mm, dd MMM yyyy", Locale.US)

        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_dialog_time_in, null as ViewGroup?, false)
        view.findViewById<TextView>(R.id.btnCheckIn).setOnClickListener {
            alertDialog.dismiss()
            navigation()
        }
        view.findViewById<TextView>(R.id.tvDateTime).text = dateFormat.format(Date())

        alertDialogBuilder.setView(view)
        alertDialogBuilder.setCancelable(false)
        alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}