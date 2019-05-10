package jp.sasuraiusagi3.nippohub_daily.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.listeners.ButtonToBackClickListener
import jp.sasuraiusagi3.nippohub_daily.models.DailyReport
import jp.sasuraiusagi3.nippohub_daily.utils.AccountManager

class DailyReportShowActivity : AppCompatActivity() {
    companion object {
        const val DAILY_REPORT = "dailyReport"
    }

    override fun onStart() {
        super.onStart()

        if (!AccountManager.didSignIn()) {
            val intent = Intent(this, SignInActivity::class.java)

            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_report_show)

        val dailyReport = this.intent.getSerializableExtra(DAILY_REPORT) as DailyReport
        val webview = findViewById<WebView>(R.id.dailyReportShowWebView)
        val btnToEdit = findViewById<Button>(R.id.dailyReportShowButtonToEdit)
        val btnToBack = findViewById<Button>(R.id.dailyReportShowButtonToBack)

        webview.webViewClient = WebClientForDailyReport(dailyReport)
        webview.settings.javaScriptEnabled = true
        webview.loadUrl("file:///android_asset/html/daily_report_show.html")

        btnToEdit.setOnClickListener {
            val intent = Intent(this, EditDailyReportActivity::class.java)

            intent.putExtra(EditDailyReportActivity.DAILY_REPORT, dailyReport)

            startActivity(intent)
        }

        btnToBack.setOnClickListener(ButtonToBackClickListener(this))
    }

    private class WebClientForDailyReport(private val dailyReport: DailyReport): WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            view ?: return

            val title = "${dailyReport.date.toString()} ${dailyReport.title}".replace("'", "\\'")
            val content = dailyReport.content
                    .replace("\\", "\\\\")
                    .replace("\n", "\\n")
                    .replace("\"", "\\\"")

            view.evaluateJavascript("rendering('$title', \"$content\")", null)
        }
    }
}
