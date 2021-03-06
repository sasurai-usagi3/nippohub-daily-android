package jp.sasuraiusagi3.nippohub_daily.presentations.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.listeners.ButtonToBackClickListener

class PrivacyActivity : AppCompatActivity() {
    companion object {
        fun build(context: Context) = Intent(context, PrivacyActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy)

        findViewById<WebView>(R.id.privacy_web_view).loadUrl("https://nippohub.com/privacy_content.html")

        findViewById<Button>(R.id.privacy_button_to_back).setOnClickListener(ButtonToBackClickListener(this))
    }
}
