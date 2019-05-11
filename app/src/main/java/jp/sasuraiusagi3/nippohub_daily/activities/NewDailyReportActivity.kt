package jp.sasuraiusagi3.nippohub_daily.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import com.google.firebase.auth.FirebaseUser
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.listeners.ButtonToBackClickListener
import jp.sasuraiusagi3.nippohub_daily.repositories.DailyReportRepository
import jp.sasuraiusagi3.nippohub_daily.repositories.UserRepository
import java.time.LocalDate

class NewDailyReportActivity : AppCompatActivity() {
    companion object {
        fun build(context: Context) = Intent(context, NewDailyReportActivity::class.java)
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
        setContentView(R.layout.activity_new_daily_report)

        val currentUser = UserRepository.currentUser ?: return
        val formDate = findViewById<DatePicker>(R.id.newDailyReportFormDate)
        val formTitle = findViewById<EditText>(R.id.newDailyReportFormTitle)
        val formContent = findViewById<EditText>(R.id.newDailyReportFormContent)
        findViewById<Button>(R.id.newDailyReportButtonSubmit).also {
            it.setOnClickListener(
                    ButtonToSubmitClickListener(this, currentUser, formDate, formTitle, formContent)
            )
        }
        findViewById<Button>(R.id.newDailyReportButtonToBack).also {
            it.setOnClickListener(ButtonToBackClickListener(this))
        }
    }

    private class ButtonToSubmitClickListener(private val activity: Activity,
                                              private val currentUser: FirebaseUser,
                                              private val formDate: DatePicker,
                                              private val formTitle: EditText,
                                              private val formContent: EditText) : View.OnClickListener {

        override fun onClick(v: View?) {
            val date = LocalDate.of(formDate.year, formDate.month + 1, formDate.dayOfMonth)
            val title = formTitle.text.toString()
            val content = formContent.text.toString()

            DailyReportRepository.create(currentUser, date, title, content)

            this.activity.finish()
        }

    }
}
