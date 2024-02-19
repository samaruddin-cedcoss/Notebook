package com.example.notebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.notebook.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNotes : AppCompatActivity() {
    lateinit var databaseRefrence : DatabaseReference
    lateinit var saveNotesBTn : Button
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        databaseRefrence = FirebaseDatabase.getInstance().reference

        auth= FirebaseAuth.getInstance()

        saveNotesBTn = findViewById(R.id.saveNotes)

        saveNotesBTn.setOnClickListener {
            val title = findViewById<EditText?>(R.id.noteTitle).text.toString()
            val description = findViewById<EditText?>(R.id.noteDesc).text.toString()


            val currentUser = auth.currentUser

            if (title.isEmpty() && description.isEmpty())
            {
                Toast.makeText(this,"Fields are required to be filled",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{



                val noteItem = NoteItem(title,description)

                currentUser?.let {user ->

                    val noteKey = databaseRefrence.child("users").child(user.uid).child("Notes").push().key
                if(noteKey!=null)
                {


                        databaseRefrence.child("users").child(user.uid).child("Notes").child(noteKey).setValue(noteItem)
                            .addOnCompleteListener {task ->
                                if (task.isSuccessful)
                                {
                                    Toast.makeText(this,"Data is saved in DB suceesfully",Toast.LENGTH_SHORT).show()
                                }
                                else{
                                    Toast.makeText(this,"Falied to save data in DB",Toast.LENGTH_SHORT).show()
                                }
                            }
                    }

                }
            }

        }

    }
}