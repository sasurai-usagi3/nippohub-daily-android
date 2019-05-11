package jp.sasuraiusagi3.nippohub_daily.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.utils.AccountManager

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        findViewById<ListView>(R.id.settingsListView).also {
            it.onItemClickListener = SettingsListItemClickListener(this)
        }
    }

    private class SettingsListItemClickListener(private val context: Context): AdapterView.OnItemClickListener {
        private val POSITION_AGREEMENTS = 0
        private val POSITION_PRIVACY = 1
        private val POSITION_SIGNIN = 2

        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val intent = when(position) {
                POSITION_AGREEMENTS -> Intent(this.context, AgreementsActivity::class.java)
                POSITION_PRIVACY -> Intent(this.context, PrivacyActivity::class.java)
                POSITION_SIGNIN -> Intent(this.context, SignInActivity::class.java)
                else -> throw RuntimeException() // TODO: 独自の例外に変える
            }

            if (position == POSITION_SIGNIN) {
                AccountManager.signOut()
            }

            this.context.startActivity(intent)
        }

    }
}
