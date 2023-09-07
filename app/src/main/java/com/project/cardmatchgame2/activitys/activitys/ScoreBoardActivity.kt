package com.project.cardmatchgame2.activitys.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.project.cardmatchgame2.R
import com.project.cardmatchgame2.activitys.model.Score
import com.project.cardmatchgame2.activitys.adapter.ScoreRvAdapter

class ScoreBoardActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ScoreRvAdapter
    private val scoreList: MutableList<Score> = mutableListOf()
    private lateinit var scoresRef: DatabaseReference
    private lateinit var valueEventListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_board)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ScoreRvAdapter(scoreList)
        recyclerView.adapter = adapter

        // Firebase veritabanı referansını al
        scoresRef = FirebaseDatabase.getInstance().getReference("Scores")

        // ValueEventListener oluştur ve atama yap
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Skor listesini temizle
                scoreList.clear()

                // Veritabanındaki her skor için döngü
                for (scoreSnapshot in snapshot.children) {
                    // Skoru al
                    val score = scoreSnapshot.getValue(Score::class.java)
                    score?.let {
                        // Skor listesine ekle
                        scoreList.add(it)
                    }
                }

                // Adapter'a skor listesini güncelle
                adapter.setScoreList(scoreList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Veri alma işlemi iptal edildiğinde veya başarısız olduğunda yapılacaklar
                Toast.makeText(this@ScoreBoardActivity, "Skorlar alınamadı.", Toast.LENGTH_SHORT).show()
            }
        }

        // ValueEventListener'ı scoresRef üzerindeki veri değişikliklerini dinlemek için ekle
        scoresRef.addValueEventListener(valueEventListener)
    }

    override fun onDestroy() {
        // valueEventListener'ü kaldır veya iptal et
        if (::valueEventListener.isInitialized) {
            scoresRef.removeEventListener(valueEventListener)
        }
        super.onDestroy()
    }
}
