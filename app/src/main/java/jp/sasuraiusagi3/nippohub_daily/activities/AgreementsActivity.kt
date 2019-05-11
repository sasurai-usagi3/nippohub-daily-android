package jp.sasuraiusagi3.nippohub_daily.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

        findViewById<WebView>(R.id.agreementsWebview).apply {
            this.loadUrl("https://nippohub.com/agreements_content.html")
        }
        findViewById<Button>(R.id.agreementsButtonToBack).also {
            it.setOnClickListener(ButtonToBackClickListener(this))
        }
    }
}
