package jp.sasuraiusagi3.nippohub_daily.presentations.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class OkOnlyDialogFragment private constructor(private val title: String, private val description: String) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(description)
                .setPositiveButton("OK", null)
                .show()
    }

    companion object {
        fun build(title: String, description: String) = OkOnlyDialogFragment(title, description)
    }
}