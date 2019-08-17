package jp.sasuraiusagi3.nippohub_daily.presentations.fragments.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

/**
 * Created by sasurai-usagi3 on 2019/05/14.
 */

class PasswordAuthErrorDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(this.activity)
                .setTitle("認証エラー")
                .setMessage("パスワードが違います。")
                .setPositiveButton("OK", null)
                .show()
    }
}