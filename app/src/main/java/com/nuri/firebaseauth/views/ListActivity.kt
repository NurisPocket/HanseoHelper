package com.nuri.firebaseauth.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.nuri.firebaseauth.R

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lists)
        var firestore : FirebaseFirestore? = null
        var uid : String? = null

    }
}