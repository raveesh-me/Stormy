package com.example.raveesh.stormy.ui

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle

class AlertDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Oops! Sorry!")
                .setMessage("There was an error, try again")
                .setPositiveButton("OK", null)
        return builder.create()
    }
}