package jp.sasuraiusagi3.nippohub_daily.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.models.DailyReport
import jp.sasuraiusagi3.nippohub_daily.utils.AccountManager
import java.time.LocalDate

class DailyReportIndexActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_report_index)

        val currentUser = AccountManager.currentUser ?: return
        val database = FirebaseDatabase.getInstance().getReference("/users/${currentUser.uid}/daily_reports")

        database.addValueEventListener(DailyReportIndexFetcher())
    }

    class DailyReportIndexFetcher: ValueEventListener {
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
        }

    }
}
