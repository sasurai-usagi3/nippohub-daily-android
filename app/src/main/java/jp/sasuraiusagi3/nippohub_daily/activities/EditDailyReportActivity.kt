package jp.sasuraiusagi3.nippohub_daily.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.models.DailyReport
import jp.sasuraiusagi3.nippohub_daily.utils.AccountManager
import java.time.LocalDate

class EditDailyReportActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_edit_daily_report)

        val currentUser = AccountManager.currentUser ?: return
        val dailyReport = this.intent.getSerializableExtra(DAILY_REPORT) as DailyReport
        val formDate = findViewById<DatePicker>(R.id.editDailyReportFormDate)
        val formTitle = findViewById<EditText>(R.id.editDailyReportFormTitle)
        val formContent = findViewById<EditText>(R.id.editDailyReportFormContent)
        val btnToSubmit = findViewById<Button>(R.id.editDailyReportBtnSubmit)
        val database = FirebaseDatabase.getInstance().getReference("/users/${currentUser.uid}/daily_reports/${dailyReport.id}")

        formDate.updateDate(dailyReport.date.year, dailyReport.date.monthValue - 1, dailyReport.date.dayOfMonth)
        formTitle.setText(dailyReport.title)
        formContent.setText(dailyReport.content)

        btnToSubmit.setOnClickListener {
            val date = LocalDate.of(formDate.year, formDate.month + 1, formDate.dayOfMonth)

            database.setValue(
                    mapOf(
                            "date" to date.toString(),
                            "title" to formTitle.text.toString(),
                            "content" to formContent.text.toString(),
                            "createdAt" to System.currentTimeMillis()
                    )
            )

            finish()
        }
    }
}
