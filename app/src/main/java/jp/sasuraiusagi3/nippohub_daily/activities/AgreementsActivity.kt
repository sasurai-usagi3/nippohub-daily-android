package jp.sasuraiusagi3.nippohub_daily.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import jp.sasuraiusagi3.nippohub_daily.R

class AgreementsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agreements)

        val webview = findViewById<WebView>(R.id.agreementsWebview)
        val btnToBack = findViewById<Button>(R.id.agreementsButtonToBack)

        webview.loadUrl("https://nippohub.com/agreements_content.html")

        btnToBack.setOnClickListener {
            finish()
        }
    }
}
