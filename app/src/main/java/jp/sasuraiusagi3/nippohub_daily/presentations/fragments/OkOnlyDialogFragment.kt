package jp.sasuraiusagi3.nippohub_daily.presentations.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import jp.sasuraiusagi3.nippohub_daily.R

class OkOnlyDialogFragment private constructor(private val title: String, private val description: String) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = requireContext()
        return AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(description)
                .setPositiveButton(context.getString(R.string.dialog_ok), null)
                .show()
    }

    companion object {
        fun build(title: String, description: String) = OkOnlyDialogFragment(title, description)
    }
}