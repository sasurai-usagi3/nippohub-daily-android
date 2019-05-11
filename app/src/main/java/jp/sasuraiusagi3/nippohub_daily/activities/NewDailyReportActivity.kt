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
import jp.sasuraiusagi3.nippohub_daily.utils.AccountManager
import java.time.LocalDate

class NewDailyReportActivity : AppCompatActivity() {
    override fun onStart() {
        super.onStart()

        if (!AccountManager.didSignIn()) {
            val intent = Intent(this, SignInActivity::class.java)

            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_daily_report)

        val currentUser = AccountManager.currentUser ?: return
        val database = FirebaseDatabase.getInstance().getReference("/users/${currentUser.uid}/daily_reports")
        val formDate = findViewById<DatePicker>(R.id.newDailyReportFormDate)
        val formTitle = findViewById<EditText>(R.id.newDailyReportFormTitle)
        val formContent = findViewById<EditText>(R.id.newDailyReportFormContent)
        findViewById<Button>(R.id.newDailyReportButtonSubmit).also {
            it.setOnClickListener(
                    ButtonToSubmitClickListener(this, database, formDate, formTitle, formContent)
            )
        }
        findViewById<Button>(R.id.newDailyReportButtonToBack).also {
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
            val ref = database.push()

            ref.setValue(
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
