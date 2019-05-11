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
    companion object {
        fun build(context: Context) = Intent(context, SignInActivity::class.java)
    }

    override fun onStart() {
        super.onStart()

        if (AccountManager.didSignIn()) {
            val intent = DailyReportIndexActivity.build(this)

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

    private class ButtonSubmitClickListener(private val activity: Activity,
                                            private val formEmail: EditText,
                                            private val formPassword: EditText): View.OnClickListener {
        override fun onClick(v: View?) {
            AccountManager.signIn(
                    formEmail.text.toString(),
                    formPassword.text.toString(),
                    {
                        val intent = DailyReportIndexActivity.build(activity)

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

    private class ButtonToSignUpClickListener(private val activity: Activity): View.OnClickListener {
        override fun onClick(v: View?) {
            val intent = SignUpActivity.build(activity)

            activity.startActivity(intent)
            activity.finish()
        }
    }
}
