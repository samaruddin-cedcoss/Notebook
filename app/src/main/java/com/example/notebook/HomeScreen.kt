package com.example.notebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notebook.databinding.ActivityHomeScreenBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class HomeScreen : AppCompatActivity() {

    lateinit var binding: ActivityHomeScreenBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()

        val binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createNotes.setOnClickListener {
            startActivity(Intent(this@HomeScreen, AddNotes::class.java))
        }

        binding.openNotes.setOnClickListener {
            startActivity(Intent(this@HomeScreen, AllNotes::class.java))
        }

        binding.signOut.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this@HomeScreen,MainActivity::class.java))
        }
    }
}