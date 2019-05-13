package jp.sasuraiusagi3.nippohub_daily.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle

/**
 * Created by sasurai-usagi3 on 2019/05/14.
 */

class EmailAlreadyUserdErrorDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(this.activity)
                .setTitle("認証エラー")
                .setMessage("入力されたメールアドレスは登録ずみです。")
                .setPositiveButton("OK", null)
                .show()
    }
}