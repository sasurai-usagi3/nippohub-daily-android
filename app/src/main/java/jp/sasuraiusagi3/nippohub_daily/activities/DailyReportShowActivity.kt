package jp.sasuraiusagi3.nippohub_daily.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.models.DailyReport

class DailyReportShowActivity : AppCompatActivity() {
    companion object {
        const val DAILY_REPORT = "dailyReport"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_report_show)

        val dailyReport = this.intent.getSerializableExtra(DAILY_REPORT) as DailyReport
        val webview = findViewById<WebView>(R.id.dailyReportShowWebView)

        webview.webViewClient = WebClientForDailyReport(dailyReport)
        webview.settings.javaScriptEnabled = true
        webview.loadUrl("file:///android_asset/html/daily_report_show.html")
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

            view.evaluateJavascript("rendering('$title', \"$content\")", {println("--------");println(it);println("--------");})
        }
    }
}
