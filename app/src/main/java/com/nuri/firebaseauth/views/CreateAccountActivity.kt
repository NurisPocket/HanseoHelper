package com.nuri.firebaseauth.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.nuri.firebaseauth.R
import kotlinx.android.synthetic.main.activity_create_account.*


class CreateAccountActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var sign_up: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        mAuth = FirebaseAuth.getInstance()
        sign_up = findViewById(R.id.btn_register3)
        btn_register3.setOnClickListener(View.OnClickListener { signUp() })
    }


    public override fun onStart() {
        super.onStart()

        val currentUser = mAuth!!.currentUser
    }

    private fun signUp() {
        // 이메일
        val emailEditText = findViewById<EditText>(R.id.edit_email)
        val emailsub = emailEditText.text.toString()
        val domain = "@office.hanseo.ac.kr"
        val email = emailsub+domain

        // 비밀번호
        val passwordEditText = findViewById<EditText>(R.id.edit_pw)
        val password = passwordEditText.text.toString()

        mAuth!!.createUserWithEmailAndPassword(email, password)

            .addOnCompleteListener(this,
                OnCompleteListener { task ->
                    if (task.isSuccessful) {

                        Log.d(TAG, "createUserWithEmail:success")
                        val user = mAuth!!.currentUser

                        sendEmailVerification()

                        val intent = Intent(this@CreateAccountActivity, SignInActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this@CreateAccountActivity, "등록 완료", Toast.LENGTH_SHORT).show()
                        finish()

                    } else {

                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this@CreateAccountActivity, "등록 에러", Toast.LENGTH_SHORT).show()
                        return@OnCompleteListener
                    }
                })
    }

    companion object {
        private const val TAG = "SignUpActivity"
    }

    fun sendEmailVerification(){

        if(FirebaseAuth.getInstance().currentUser!!.isEmailVerified){
            Toast.makeText(this, "이메일 인증이 이미 완료되었습니다", Toast.LENGTH_LONG).show()
            return
        }

        FirebaseAuth.getInstance().currentUser!!.sendEmailVerification().addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this, "확인메일을 보냈습니다", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

}