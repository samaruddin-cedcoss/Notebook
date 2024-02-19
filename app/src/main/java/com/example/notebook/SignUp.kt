package com.example.notebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.notebook.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()

        binding.register.setOnClickListener {
            val email = binding.userEmailEditText.text.toString().trim()
            val password = binding.userPasswordEditText.text.toString().trim()

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                Toast.makeText(this@SignUp,"Fields are Required",Toast.LENGTH_SHORT).show()
            }
            else if(password.length<6) {
                Toast.makeText(this@SignUp,"Password is too short",Toast.LENGTH_SHORT).show()
            }
            else
            {
                registerUser(email,password)
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful)
            {
                Toast.makeText(this,"Registering Successfull",Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
            }
        }
    }
}