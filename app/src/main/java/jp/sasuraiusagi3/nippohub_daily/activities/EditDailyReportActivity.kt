package jp.sasuraiusagi3.nippohub_daily.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.listeners.ButtonToBackClickListener
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
        val database = FirebaseDatabase.getInstance().getReference("/users/${currentUser.uid}/daily_reports/${dailyReport.id}")
        val formDate = findViewById<DatePicker>(R.id.editDailyReportFormDate).apply {
            this.updateDate(dailyReport.date.year, dailyReport.date.monthValue - 1, dailyReport.date.dayOfMonth)
        }
        val formTitle = findViewById<EditText>(R.id.editDailyReportFormTitle).apply {
            this.setText(dailyReport.title)
        }
        val formContent = findViewById<EditText>(R.id.editDailyReportFormContent).apply {
            this.setText(dailyReport.content)
        }
        findViewById<Button>(R.id.editDailyReportButtonSubmit).also {
            it.setOnClickListener(
                    ButtonToSubmitClickListener(this, database, formDate, formTitle, formContent)
            )
        }
        findViewById<Button>(R.id.editDailyReportButtonToBack).also {
            it.setOnClickListener(ButtonToBackClickListener(this))
        }
    }

    private class ButtonToSubmitClickListener(private val activity: Activity,
                                              private val database: DatabaseReference,
                                              private val formDate: DatePicker,
                                              private val formTitle: EditText,
                                              private val formContent: EditText): View.OnClickListener {

        override fun onClick(v: View?) {
            val date = LocalDate.of(formDate.year, formDate.month + 1, formDate.dayOfMonth)

            database.setValue(
                    mapOf(
                            "date" to date.toString(),
                            "title" to formTitle.text.toString(),
                            "content" to formContent.text.toString(),
                            "createdAt" to System.currentTimeMillis()
                    )
            )

            this.activity.finish()
        }

    }
}
