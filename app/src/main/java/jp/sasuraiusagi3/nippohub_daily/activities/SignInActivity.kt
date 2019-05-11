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

class SignInActivity : AppCompatActivity() {
    override fun onStart() {
        super.onStart()

        if (AccountManager.didSignIn()) {
            val intent = Intent(this, DailyReportIndexActivity::class.java)

            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val formEmail = findViewById<EditText>(R.id.signInFormEmail)
        val formPassword = findViewById<EditText>(R.id.signInFormPassword)
        findViewById<Button>(R.id.signInButtonSubmit).also {
            it.setOnClickListener(ButtonSubmitClickListener(this, formEmail, formPassword))
        }
        findViewById<Button>(R.id.signInButtonToSignUp).also {
            it.setOnClickListener(ButtonToSignUpClickListener(this))
        }
    }

    // 戻れないようにしている
    override fun onBackPressed() {
    }

    private class ButtonSubmitClickListener(private val context: Context,
                                            private val formEmail: EditText,
                                            private val formPassword: EditText): View.OnClickListener {
        override fun onClick(v: View?) {
            AccountManager.signIn(
                    formEmail.text.toString(),
                    formPassword.text.toString(),
                    {
                        val intent = Intent(this.context, DailyReportIndexActivity::class.java)

                        this.context.startActivity(intent)
                    },
                    {
                        println("|------|")
                        println(it)
                        println("|------|")
                    }
            )
        }
    }

    private class ButtonToSignUpClickListener(private val activity: Activity): View.OnClickListener {
        override fun onClick(v: View?) {
            val intent = Intent(this.activity, SignUpActivity::class.java)

            activity.startActivity(intent)
            activity.finish()
        }
    }
}
