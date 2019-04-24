package jp.sasuraiusagi3.nippohub_daily.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.utils.AccountManager

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val formEmail = findViewById<EditText>(R.id.signUpFormEmail)
        val formPassword = findViewById<EditText>(R.id.signUpFormPassword)
        val formPasswordConfirmation = findViewById<EditText>(R.id.signUpFormPasswordConfirmation)
        val btnSubmit = findViewById<Button>(R.id.signUpButtonSubmit)

        btnSubmit.setOnClickListener {
            if (formPassword.text.toString() == formPasswordConfirmation.text.toString()) {
                AccountManager.signUp(
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
}
