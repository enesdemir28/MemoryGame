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
        // RecyclerView'ı ayarla
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserRvAdapter(userList)
        recyclerView.adapter = adapter
        // Firebase veritabanı referansını al
        usersRef = FirebaseDatabase.getInstance().getReference("Users")
        // ValueEventListener'ı tanımla
        valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Kullanıcı listesini temizle
                userList.clear()
                // Veritabanındaki her kullanıcı için döngü
                for (userSnapshot in snapshot.children) {
                    // Kullanıcıyı al
                    val user = userSnapshot.getValue(User::class.java)
                    user?.let {
                        // Kullanıcı listesine ekle
                        userList.add(it)
                    }
                }
                // Adaptere kullanıcı listesini ayarla
                adapter.setUserList(userList)
            }
            override fun onCancelled(error: DatabaseError) {
                // Veri alma işlemi iptal edildiğinde veya başarısız olduğunda yapılacaklar
                Toast.makeText(this@PlayersActivity, "Kullanıcılar alınamadı.", Toast.LENGTH_SHORT).show()
            }
        }
        // ValueEventListener'ı veritabanına ekle
        usersRef.addValueEventListener(valueEventListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        // ValueEventListener'ı kaldır
        usersRef.removeEventListener(valueEventListener)
    }
}