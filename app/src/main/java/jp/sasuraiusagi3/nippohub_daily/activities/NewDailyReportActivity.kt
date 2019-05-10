package jp.sasuraiusagi3.nippohub_daily.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
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
        val formDate = findViewById<DatePicker>(R.id.newDailyReportFormDate)
        val formTitle = findViewById<EditText>(R.id.newDailyReportFormTitle)
        val formContent = findViewById<EditText>(R.id.newDailyReportFormContent)
        val btnToSubmit = findViewById<Button>(R.id.newDailyReportButtonSubmit)
        val btnToBack = findViewById<Button>(R.id.newDailyReportButtonToBack)
        val database = FirebaseDatabase.getInstance().getReference("/users/${currentUser.uid}/daily_reports")

        btnToSubmit.setOnClickListener {
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

            finish()
        }

        btnToBack.setOnClickListener(ButtonToBackClickListener(this))
    }
}
