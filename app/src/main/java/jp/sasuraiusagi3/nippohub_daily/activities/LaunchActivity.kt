package jp.sasuraiusagi3.nippohub_daily.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.repositories.UserRepository

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        val intent = if (UserRepository.currentUser != null) {
            DailyReportIndexActivity.build(this)
        } else {
            SignInActivity.build(this)
        }

        startActivity(intent)

        finish()
    }
}
