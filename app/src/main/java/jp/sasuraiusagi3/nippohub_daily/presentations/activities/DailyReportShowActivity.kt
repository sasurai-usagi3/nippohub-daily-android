package jp.sasuraiusagi3.nippohub_daily.presentations.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.listeners.ButtonToBackClickListener
import jp.sasuraiusagi3.nippohub_daily.models.DailyReport
import jp.sasuraiusagi3.nippohub_daily.repositories.UserRepository

class DailyReportShowActivity : AppCompatActivity() {
    companion object {
        const val DAILY_REPORT = "dailyReport"

        fun build(context: Context, dailyReport: DailyReport) =
                Intent(context, DailyReportShowActivity::class.java).putExtra(DAILY_REPORT, dailyReport)
    }

    override fun onStart() {
        super.onStart()

        if (!UserRepository.didSignIn()) {
            val intent = SignInActivity.build(this)

            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_report_show)

        val dailyReport = intent.getSerializableExtra(DAILY_REPORT) as DailyReport
        setDailyReport(dailyReport)
        findViewById<Button>(R.id.daily_report_show_button_to_back).setOnClickListener(ButtonToBackClickListener(this))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        data ?: return

        val dailyReport = data.getSerializableExtra(DAILY_REPORT) as DailyReport
        setDailyReport(dailyReport)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setDailyReport(dailyReport: DailyReport) {

        findViewById<WebView>(R.id.daily_report_show_web_view).apply {
            webViewClient = WebClientForDailyReport(dailyReport)
            settings.javaScriptEnabled = true
            loadUrl("file:///android_asset/html/daily_report_show.html")
        }
        findViewById<Button>(R.id.dailyReportShowButtonToEdit).setOnClickListener(ButtonToEditClickListener(this, dailyReport))
    }

    private class WebClientForDailyReport(private val dailyReport: DailyReport) : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            view ?: return

            val title = "${dailyReport.date} ${dailyReport.title}".replace("'", "\\'")
            val content = dailyReport.content
                    .replace("\\", "\\\\")
                    .replace("\n", "\\n")
                    .replace("\"", "\\\"")

            view.evaluateJavascript("rendering('$title', \"$content\")", null)
        }
    }

    private class ButtonToEditClickListener(private val activity: Activity, private val dailyReport: DailyReport) : View.OnClickListener {
        override fun onClick(v: View?) {
            val intent = PostDailyReportActivity.build(activity, dailyReport)

            activity.startActivityForResult(intent, 0)
        }
    }
}
