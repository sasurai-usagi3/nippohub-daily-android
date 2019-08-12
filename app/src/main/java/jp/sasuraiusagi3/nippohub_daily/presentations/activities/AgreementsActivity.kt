package jp.sasuraiusagi3.nippohub_daily.presentations.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebView
import android.widget.Button
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.listeners.ButtonToBackClickListener

class AgreementsActivity : AppCompatActivity() {
    companion object {
        fun build(context: Context) = Intent(context, AgreementsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agreements)

        findViewById<WebView>(R.id.agreements_web_view).apply {
            this.loadUrl("https://nippohub.com/agreements_content.html")
        }
        findViewById<Button>(R.id.agreements_button_to_back).apply {
            setOnClickListener(ButtonToBackClickListener(this@AgreementsActivity))
        }
    }
}
