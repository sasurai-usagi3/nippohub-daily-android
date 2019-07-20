package jp.sasuraiusagi3.nippohub_daily.presentations.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.presentations.fragments.dialogs.PasswordAuthErrorDialogFragment
import jp.sasuraiusagi3.nippohub_daily.repositories.UserRepository

class SignInActivity : AppCompatActivity() {
    companion object {
        fun build(context: Context) = Intent(context, SignInActivity::class.java)
    }

    override fun onStart() {
        super.onStart()

        if (UserRepository.didSignIn()) {
            val intent = DailyReportIndexActivity.build(this)

            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val formEmail = findViewById<EditText>(R.id.sign_in_form_email)
        val formPassword = findViewById<EditText>(R.id.sign_in_form_password)
        findViewById<Button>(R.id.sign_in_button_submit).also {
            it.setOnClickListener(ButtonSubmitClickListener(this, formEmail, formPassword))
        }
        findViewById<Button>(R.id.sign_in_button_to_sign_up).also {
            it.setOnClickListener(ButtonToSignUpClickListener(this))
        }
    }

    // 戻れないようにしている
    override fun onBackPressed() {
    }

    private class ButtonSubmitClickListener(private val activity: Activity,
                                            private val formEmail: EditText,
                                            private val formPassword: EditText) : View.OnClickListener {
        override fun onClick(v: View?) {
            UserRepository.signIn(
                    formEmail.text.toString(),
                    formPassword.text.toString(),
                    {

                        if (!it.isSuccessful) {
                            return@signIn
                        }

                        val intent = DailyReportIndexActivity.build(activity)

                        this.activity.startActivity(intent)
                        this.activity.finish()
                    },
                    {
                        // TODO: ネットワークエラーとわけたい
                        val dialog = PasswordAuthErrorDialogFragment()

                        dialog.show(activity.fragmentManager, null)
                    }
            )
        }
    }

    private class ButtonToSignUpClickListener(private val activity: Activity) : View.OnClickListener {
        override fun onClick(v: View?) {
            val intent = SignUpActivity.build(activity)

            activity.startActivity(intent)
            activity.finish()
        }
    }
}
