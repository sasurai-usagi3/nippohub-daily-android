package jp.sasuraiusagi3.nippohub_daily.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
        val btnSubmit = findViewById<Button>(R.id.signInButtonSubmit)
        val btnToSignUp = findViewById<Button>(R.id.signInButtonToSignUp)

        btnSubmit.setOnClickListener {
            AccountManager.signIn(
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

        btnToSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)

            startActivity(intent)
            finish()
        }
    }

    // 戻れないようにしている
    override fun onBackPressed() {
    }
}
