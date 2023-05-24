package com.project.cardmatchgame2.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.project.cardmatchgame2.R

class ScoreBoardActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var scoreAdapter: ScoreRvAdapter
    private lateinit var scoreList: MutableList<Score>
    private lateinit var scoresRef: DatabaseReference
    private lateinit var scoreTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_board)
        // Firebase veritabanı referansını al
        scoresRef = FirebaseDatabase.getInstance().getReference("Scores")

        // Skorları RecyclerView için boş bir listeyle başlat
        scoreList = mutableListOf()

        // RecyclerView ve adapteri yapılandır
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        scoreAdapter = ScoreRvAdapter(scoreList)
        recyclerView.adapter = scoreAdapter

        // Skorları Firebase Realtime Database'den al ve RecyclerView'e ata
        scoresRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                scoreList.clear()
                for (dataSnapshot in snapshot.children) {
                    val userId = dataSnapshot.key
                    val score = dataSnapshot.value as Int
                    if (userId != null) {
                        scoreList.add(Score(userId, score))
                    }
                }
                scoreAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // İstek iptal edildiğinde veya başarısız olduğunda yapılacaklar
                Toast.makeText(this@ScoreBoardActivity, "Skorlar alınamadı.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}