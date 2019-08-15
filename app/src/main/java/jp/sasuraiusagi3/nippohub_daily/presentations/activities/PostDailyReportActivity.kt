package jp.sasuraiusagi3.nippohub_daily.presentations.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.listeners.ButtonToBackClickListener
import jp.sasuraiusagi3.nippohub_daily.models.DailyReport
import jp.sasuraiusagi3.nippohub_daily.repositories.DailyReportRepository
import jp.sasuraiusagi3.nippohub_daily.repositories.UserRepository
import java.time.LocalDate

class PostDailyReportActivity : AppCompatActivity() {
    companion object {
        const val DAILY_REPORT = "dailyReport"

        fun build(context: Context, dailyReport: DailyReport? = null) =
                Intent(context, PostDailyReportActivity::class.java).apply {
                    putExtra(DAILY_REPORT, dailyReport)
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_post_daily_report)

        val currentUser = UserRepository.currentUser ?: return
        val dailyReport = intent.getSerializableExtra(DAILY_REPORT) as DailyReport?
        val formDate = findViewById<DatePicker>(R.id.post_daily_report_form_date).apply {
            dailyReport?.let {
                updateDate(it.date.year, it.date.monthValue - 1, it.date.dayOfMonth)
            }
        }
        val formTitle = findViewById<EditText>(R.id.post_daily_report_form_title).apply {
            setText(dailyReport?.title)
        }
        val formContent = findViewById<EditText>(R.id.post_daily_report_form_content).apply {
            setText(dailyReport?.content)
        }

        findViewById<Button>(R.id.post_daily_report_button_submit).apply {
            text = if (dailyReport != null) {
                "更新"
            } else {
                "作成"
            }

            setOnClickListener(
                    ButtonToSubmitClickListener(this@PostDailyReportActivity, currentUser, dailyReport, formDate, formTitle, formContent)
            )
        }

        findViewById<Button>(R.id.post_daily_report_button_to_back).setOnClickListener(ButtonToBackClickListener(this))

    }

    private class ButtonToSubmitClickListener(private val activity: Activity,
                                              private val currentUser: FirebaseUser,
                                              private val dailyReport: DailyReport?,
                                              private val formDate: DatePicker,
                                              private val formTitle: EditText,
                                              private val formContent: EditText) : View.OnClickListener {

        override fun onClick(v: View?) {
            val date = LocalDate.of(formDate.year, formDate.month + 1, formDate.dayOfMonth)
            val title = formTitle.text.toString()
            val content = formContent.text.toString()

            if (dailyReport != null) {
                val dailyReport = dailyReport.copy(
                        date = date,
                        title = formTitle.text.toString(),
                        content = formContent.text.toString()
                )
                val intent = Intent()

                DailyReportRepository.update(currentUser, dailyReport)

                intent.putExtra(DailyReportShowActivity.DAILY_REPORT, dailyReport)
                activity.setResult(Activity.RESULT_OK, intent)
            } else {
                DailyReportRepository.create(currentUser, date, title, content)
            }

            activity.finish()
        }

    }
}