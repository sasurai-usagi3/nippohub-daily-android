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
import jp.sasuraiusagi3.nippohub_daily.dialogs.EmailAlreadyUserdErrorDialogFragment
import jp.sasuraiusagi3.nippohub_daily.dialogs.PasswordAuthErrorDialogFragment
import jp.sasuraiusagi3.nippohub_daily.repositories.UserRepository

class SignUpActivity : AppCompatActivity() {
    companion object {
        fun build(context: Context) = Intent(context, SignUpActivity::class.java)
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
        setContentView(R.layout.activity_sign_up)

        val formEmail = findViewById<EditText>(R.id.sign_up_form_email)
        val formPassword = findViewById<EditText>(R.id.sign_up_form_password)
        val formPasswordConfirmation = findViewById<EditText>(R.id.sign_up_form_password_confirmation)
        findViewById<Button>(R.id.sign_up_button_submit).also {
            it.setOnClickListener(
                    ButtonSubmitClickListener(this, formEmail, formPassword, formPasswordConfirmation)
            )
        }
        findViewById<Button>(R.id.sign_up_button_to_privacy).also {
            it.setOnClickListener(ButtonToPrivacyClickListener(this))
        }
        findViewById<Button>(R.id.sign_up_button_to_agreements).also {
            it.setOnClickListener(ButtonToAgreementsClickListener(this))
        }
    }

    private class ButtonSubmitClickListener(private val activity: Activity,
                                            private val formEmail: EditText,
                                            private val formPassword: EditText,
                                            private val formPasswordConfirmation: EditText) : View.OnClickListener {
        override fun onClick(v: View?) {
            if (formPassword.text.toString() != formPasswordConfirmation.text.toString()) {
                val dialog = PasswordAuthErrorDialogFragment()

                dialog.show(this.activity.fragmentManager, null)
                return
            }

            UserRepository.signUp(
                    formEmail.text.toString(),
                    formPassword.text.toString(),
                    {
                        if (!it.isSuccessful) {
                            return@signUp
                        }

                        val intent = DailyReportIndexActivity.build(activity)

                        this.activity.startActivity(intent)
                        this.activity.finish()
                    },
                    {
                        val dialog = EmailAlreadyUserdErrorDialogFragment()

                        dialog.show(this.activity.fragmentManager, null)
                    }
            )
        }
    }

    private class ButtonToAgreementsClickListener(private val context: Context) : View.OnClickListener {
        override fun onClick(v: View?) {
            val intent = AgreementsActivity.build(context)

            context.startActivity(intent)
        }
    }

    private class ButtonToPrivacyClickListener(private val context: Context) : View.OnClickListener {
        override fun onClick(v: View?) {
            val intent = PrivacyActivity.build(context)

            context.startActivity(intent)
        }
    }
}
