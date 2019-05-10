package jp.sasuraiusagi3.nippohub_daily.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.listeners.ButtonToBackClickListener

class PrivacyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy)

        val webview = findViewById<WebView>(R.id.privacyWebview)
        val btnToBack = findViewById<Button>(R.id.privacyButtonToBack)

        webview.loadUrl("https://nippohub.com/privacy_content.html")

        btnToBack.setOnClickListener(ButtonToBackClickListener(this))
    }
}
