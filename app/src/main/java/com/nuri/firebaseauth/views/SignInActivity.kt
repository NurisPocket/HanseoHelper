package com.nuri.firebaseauth.views

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.nuri.firebaseauth.R
import com.nuri.firebaseauth.extensions.Extensions.toast
import com.nuri.firebaseauth.utils.FirebaseUtils.firebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    lateinit var signInEmail: String
    lateinit var signInPassword: String
    lateinit var signInInputsArray: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signInInputsArray = arrayOf(edit_email, edit_pw)
        btn_register.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
            finish()
        }

        btn_login.setOnClickListener {
            signInUser(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun notEmpty(): Boolean = signInEmail.isNotEmpty() && signInPassword.isNotEmpty()

    private fun signInUser(intent: Intent) {
        signInEmail = edit_email.text.toString().trim()
        signInPassword = edit_pw.text.toString().trim()

        if (notEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(signInEmail, signInPassword)
                .addOnCompleteListener { signIn ->
                    if (signIn.isSuccessful) {
                        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                        toast("한서헬퍼에 오신것을 환영합니다.")
                        finish()
                    } else {
                        toast("이메일 또는 비밀번호가 올바르지 않습니다.")
                    }
                }
        } else {
            signInInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        }
    }
}
