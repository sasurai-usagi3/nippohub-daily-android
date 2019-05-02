package jp.sasuraiusagi3.nippohub_daily.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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

        println("------------")
        println(dailyReport)
        println("------------")
    }
}
