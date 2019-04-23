package jp.sasuraiusagi3.nippohub_daily.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.utils.AccountManager

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val formEmail = findViewById<EditText>(R.id.signInFormEmail)
        val formPassword = findViewById<EditText>(R.id.signInFormPassword)
        val btnSubmit = findViewById<Button>(R.id.signInButtonSubmit)

        btnSubmit.setOnClickListener {
            AccountManager.signIn(
                    formEmail.text.toString(),
                    formPassword.text.toString(),
                    {
                        println("------")
                        println(it)
                        println("------")
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
