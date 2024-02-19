package com.example.notebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notebook.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllNotes : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference
    lateinit var recyclerView: RecyclerView

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_notes)

        recyclerView = findViewById(R.id.recycler_view)

        auth = FirebaseAuth.getInstance()


        databaseReference=FirebaseDatabase.getInstance().reference

        val currentUser = auth.currentUser

        currentUser?.let { user ->
            val noteDatabaseReference = databaseReference.child("users").child(user.uid).child("Notes")

            noteDatabaseReference.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val notes = mutableListOf<NoteItem>()

                    for(dataSnapshot in snapshot.children)
                    {
                        val note = dataSnapshot.getValue(NoteItem::class.java)
                        note?.let {
                            notes.add(it)
                        }
                    }
                    val adapter=NoteAdapter(notes)
                    recyclerView.adapter=adapter
                    recyclerView.layoutManager=LinearLayoutManager(this@AllNotes)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        }


    }
}