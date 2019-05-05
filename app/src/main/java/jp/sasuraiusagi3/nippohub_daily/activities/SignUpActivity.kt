package jp.sasuraiusagi3.nippohub_daily.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.utils.AccountManager

class SignUpActivity : AppCompatActivity() {
    override fun onStart() {
        super.onStart()

        if (AccountManager.didSignIn()) {
            val intent = Intent(this, DailyReportIndexActivity::class.java)

            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val formEmail = findViewById<EditText>(R.id.signUpFormEmail)
        val formPassword = findViewById<EditText>(R.id.signUpFormPassword)
        val formPasswordConfirmation = findViewById<EditText>(R.id.signUpFormPasswordConfirmation)
        val btnSubmit = findViewById<Button>(R.id.signUpButtonSubmit)
        val btnToPrivacy = findViewById<Button>(R.id.signUpButtonToPrivacy)
        val btnToTerm = findViewById<Button>(R.id.signUpButtonToAgreements)

        btnSubmit.setOnClickListener {
            if (formPassword.text.toString() == formPasswordConfirmation.text.toString()) {
                AccountManager.signUp(
                        formEmail.text.toString(),
                        formPassword.text.toString(),
                        {
                            val intent = Intent(this, DailyReportIndexActivity::class.java)

                            startActivity(intent)
                        },
                        {
                            println("|------|")
                            println(it)
                            println("|------|")
                        }
                )
            }
        }

        btnToPrivacy.setOnClickListener {
            val intent = Intent(this, PrivacyActivity::class.java)

            startActivity(intent)
        }

        btnToTerm.setOnClickListener {
            val intent = Intent(this, AgreementsActivity::class.java)

            startActivity(intent)
        }
    }
}
