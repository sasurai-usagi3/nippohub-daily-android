package jp.sasuraiusagi3.nippohub_daily.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
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
        findViewById<Button>(R.id.signUpButtonSubmit).also {
            it.setOnClickListener(
                    ButtonSubmitClickListener(this, formEmail, formPassword, formPasswordConfirmation)
            )
        }
        findViewById<Button>(R.id.signUpButtonToPrivacy).also {
            it.setOnClickListener(ButtonToPrivacyClickListener(this))
        }
        findViewById<Button>(R.id.signUpButtonToAgreements).also {
            it.setOnClickListener(ButtonToAgreementsClickListener(this))
        }
    }

    private class ButtonSubmitClickListener(private val activity: Activity,
                                            private val formEmail: EditText,
                                            private val formPassword: EditText,
                                            private val formPasswordConfirmation: EditText): View.OnClickListener {
        override fun onClick(v: View?) {
            if (formPassword.text.toString() == formPasswordConfirmation.text.toString()) {
                AccountManager.signUp(
                        formEmail.text.toString(),
                        formPassword.text.toString(),
                        {
                            val intent = Intent(this.activity, DailyReportIndexActivity::class.java)

                            this.activity.startActivity(intent)
                            this.activity.finish()
                        },
                        {
                            println("|------|")
                            println(it)
                            println("|------|")
                        }
                )
            }
        }
    }

    private class ButtonToAgreementsClickListener(private val context: Context): View.OnClickListener {
        override fun onClick(v: View?) {
            val intent = Intent(this.context, AgreementsActivity::class.java)

            context.startActivity(intent)
        }
    }

    private class ButtonToPrivacyClickListener(private val context: Context): View.OnClickListener {
        override fun onClick(v: View?) {
            val intent = Intent(this.context, PrivacyActivity::class.java)

            context.startActivity(intent)
        }
    }
}
