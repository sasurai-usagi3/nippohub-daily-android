package jp.sasuraiusagi3.nippohub_daily.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.adapters.DailyReportListAdapter
import jp.sasuraiusagi3.nippohub_daily.models.DailyReport
import jp.sasuraiusagi3.nippohub_daily.repositories.DailyReportRepository
import jp.sasuraiusagi3.nippohub_daily.repositories.UserRepository

class DailyReportIndexActivity : AppCompatActivity() {

    companion object {
        fun build(context: Context) = Intent(context, DailyReportIndexActivity::class.java)
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
        setContentView(R.layout.activity_daily_report_index)

        val currentUser = UserRepository.currentUser ?: return
        val adapter = DailyReportListAdapter(this)
        findViewById<Button>(R.id.dailyReportIndexButtonToNew).also {
            it.setOnClickListener(ButtonToNewClickListener(this))
        }
        findViewById<Button>(R.id.dailyReportIndexButtonToSettings).also {
            it.setOnClickListener(ButtonToSettingsClickListener(this))
        }
        findViewById<ListView>(R.id.dailyReportIndexDailyReportList).also {
            it.adapter = adapter
            it.onItemClickListener = DailyReportListClickListener(this)
        }

        DailyReportRepository.fetch(currentUser) {
            adapter.dailyReports = it
            adapter.notifyDataSetChanged()
        }
    }

    private class DailyReportListClickListener(private val context: Context) : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val dailyReport = parent?.adapter?.getItem(position) as DailyReport
            val intent = DailyReportShowActivity.build(context, dailyReport)

            context.startActivity(intent)
        }
    }

    private class ButtonToSettingsClickListener(private val context: Context) : View.OnClickListener {
        override fun onClick(v: View?) {
            val intent = SettingsActivity.build(context)

            this.context.startActivity(intent)
        }
    }

    private class ButtonToNewClickListener(private val context: Context) : View.OnClickListener {
        override fun onClick(v: View?) {
            val intent = NewDailyReportActivity.build(context)

            this.context.startActivity(intent)
        }
    }
}
