package jp.sasuraiusagi3.nippohub_daily.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.adapters.DailyReportListAdapter
import jp.sasuraiusagi3.nippohub_daily.models.DailyReport
import jp.sasuraiusagi3.nippohub_daily.utils.AccountManager
import java.time.LocalDate

class DailyReportIndexActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_report_index)

        val currentUser = AccountManager.currentUser ?: return
        val database = FirebaseDatabase.getInstance().getReference("/users/${currentUser.uid}/daily_reports")
        val dailyReportList = findViewById<ListView>(R.id.dailyReportIndexDailyReportList)
        val btnToNew = findViewById<Button>(R.id.dailyReportIndexBtnToNew)
        val adapter = DailyReportListAdapter(this)

        dailyReportList.adapter = adapter

        adapter.notifyDataSetChanged()

        database.addValueEventListener(DailyReportIndexFetcher(adapter))

        btnToNew.setOnClickListener {
            val intent = Intent(this, NewDailyReportActivity::class.java)

            startActivity(intent)
        }

        dailyReportList.onItemClickListener = DailyReportListClickListener(this)
    }

    private class DailyReportIndexFetcher(private val adapter: DailyReportListAdapter): ValueEventListener {
        override fun onCancelled(r: DatabaseError) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onDataChange(r: DataSnapshot) {
            val dailyReports = r.children.map {
                DailyReport(
                        it.key!!,
                        LocalDate.parse(it.child("date").value as String),
                        it.child("title").value as String,
                        it.child("content").value as String
                )
            }

            adapter.dailyReports = dailyReports
            adapter.notifyDataSetChanged()
        }

    }

    private class DailyReportListClickListener(private val context: Context): AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val intent = Intent(context, DailyReportShowActivity::class.java)
            val dailyReport = parent?.adapter?.getItem(position) as DailyReport

            intent.putExtra(DailyReportShowActivity.DAILY_REPORT, dailyReport)

            context.startActivity(intent)
        }
    }
}
