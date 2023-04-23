package com.nuri.firebaseauth.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.nuri.firebaseauth.R

class start : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_start)
            loadSplashScreen()
        }

        private fun loadSplashScreen(){
            Handler().postDelayed({

                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }
    }
