package jp.sasuraiusagi3.nippohub_daily.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import com.google.firebase.auth.FirebaseUser
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.listeners.ButtonToBackClickListener
import jp.sasuraiusagi3.nippohub_daily.models.DailyReport
import jp.sasuraiusagi3.nippohub_daily.repositories.DailyReportRepository
import jp.sasuraiusagi3.nippohub_daily.repositories.UserRepository
import java.time.LocalDate

class EditDailyReportActivity : AppCompatActivity() {
    companion object {
        const val DAILY_REPORT = "dailyReport"

        fun build(context: Context, dailyReport: DailyReport) =
                Intent(context, EditDailyReportActivity::class.java).apply {
                    this.putExtra(DAILY_REPORT, dailyReport)
                }
    }

    override fun onStart() {
        super.onStart()

        if (!UserRepository.didSignIn()) {
            val intent = Intent(this, SignInActivity::class.java)

            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_daily_report)

        val currentUser = UserRepository.currentUser ?: return
        val dailyReport = this.intent.getSerializableExtra(DAILY_REPORT) as DailyReport
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
                    ButtonToSubmitClickListener(this, currentUser, dailyReport, formDate, formTitle, formContent)
            )
        }
        findViewById<Button>(R.id.editDailyReportButtonToBack).also {
            it.setOnClickListener(ButtonToBackClickListener(this))
        }
    }

    private class ButtonToSubmitClickListener(private val activity: Activity,
                                              private val currentUser: FirebaseUser,
                                              private val dailyReport: DailyReport,
                                              private val formDate: DatePicker,
                                              private val formTitle: EditText,
                                              private val formContent: EditText) : View.OnClickListener {

        override fun onClick(v: View?) {
            val date = LocalDate.of(formDate.year, formDate.month + 1, formDate.dayOfMonth)
            val newDailyReport = dailyReport.copy(
                    date = date,
                    title = formTitle.text.toString(),
                    content = formContent.text.toString()
            )

            DailyReportRepository.update(currentUser, newDailyReport)

            this.activity.finish()
        }

    }
}
