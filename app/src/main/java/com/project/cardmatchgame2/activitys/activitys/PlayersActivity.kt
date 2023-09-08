package com.project.cardmatchgame2.activitys.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.project.cardmatchgame2.R
import com.project.cardmatchgame2.activitys.model.User
import com.project.cardmatchgame2.activitys.adapter.UserRvAdapter

class PlayersActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserRvAdapter
    private val userList: MutableList<User> = mutableListOf()
    private lateinit var valueEventListener: ValueEventListener
    private lateinit var usersRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserRvAdapter(userList)
        recyclerView.adapter = adapter

        usersRef = FirebaseDatabase.getInstance().getReference("Users")

        valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()

                for (userSnapshot in snapshot.children) {

                    val user = userSnapshot.getValue(User::class.java)
                    user?.let {

                        userList.add(it)
                    }
                }

                adapter.setUserList(userList)
            }
            override fun onCancelled(error: DatabaseError) {

                Toast.makeText(this@PlayersActivity, "Kullanıcılar alınamadı.", Toast.LENGTH_SHORT).show()
            }
        }

        usersRef.addValueEventListener(valueEventListener)
    }

    override fun onDestroy() {
        super.onDestroy()

        usersRef.removeEventListener(valueEventListener)
    }
}