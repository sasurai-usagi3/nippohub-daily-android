package jp.sasuraiusagi3.nippohub_daily.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.utils.AccountManager

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        val activity = if (AccountManager.currentUser != null) {
            DailyReportIndexActivity::class.java
        } else {
            SignInActivity::class.java
        }
        val intent = Intent(this, activity)

        startActivity(intent)

        finish()
    }
}
