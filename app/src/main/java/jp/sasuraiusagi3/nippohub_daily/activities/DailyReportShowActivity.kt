package jp.sasuraiusagi3.nippohub_daily.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
        findViewById<WebView>(R.id.dailyReportShowWebView).apply {
            this.webViewClient = WebClientForDailyReport(dailyReport)
            this.settings.javaScriptEnabled = true
            this.loadUrl("file:///android_asset/html/daily_report_show.html")
        }
        findViewById<Button>(R.id.dailyReportShowButtonToEdit).also {
            it.setOnClickListener(ButtonToEditClickListener(this, dailyReport))
        }
        findViewById<Button>(R.id.dailyReportShowButtonToBack).also {
            it.setOnClickListener(ButtonToBackClickListener(this))
        }
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

    private class ButtonToEditClickListener(private val context: Context, private val dailyReport: DailyReport): View.OnClickListener {
        override fun onClick(v: View?) {
            val intent = Intent(this.context, EditDailyReportActivity::class.java)

            intent.putExtra(EditDailyReportActivity.DAILY_REPORT, dailyReport)

            this.context.startActivity(intent)
        }
    }
}
