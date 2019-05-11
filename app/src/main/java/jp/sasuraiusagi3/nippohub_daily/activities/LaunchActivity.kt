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

        val intent = if (AccountManager.currentUser != null) {
            DailyReportIndexActivity.build(this)
        } else {
            SignInActivity.build(this)
        }

        startActivity(intent)

        finish()
    }
}
