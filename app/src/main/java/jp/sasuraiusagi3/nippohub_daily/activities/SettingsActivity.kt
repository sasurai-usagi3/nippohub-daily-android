package jp.sasuraiusagi3.nippohub_daily.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.utils.AccountManager

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val settingsList = findViewById<ListView>(R.id.settingsListView)

        // TODO: 定数に置き換える
        settingsList.setOnItemClickListener { parent, view, position, id ->
            val intent = when(position) {
                0 -> Intent(this, AgreementsActivity::class.java)
                1 -> Intent(this, PrivacyActivity::class.java)
                2 -> Intent(this, SignInActivity::class.java)
                else -> throw RuntimeException() // TODO: 独自の例外に変える
            }

            if (position == 2) {
                AccountManager.signOut()
            }

            startActivity(intent)
        }
    }
}
