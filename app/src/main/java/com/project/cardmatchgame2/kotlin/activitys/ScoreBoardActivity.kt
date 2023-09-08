package com.project.cardmatchgame2.kotlin.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.project.cardmatchgame2.R
import com.project.cardmatchgame2.kotlin.model.Score
import com.project.cardmatchgame2.kotlin.adapter.ScoreRvAdapter

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


        scoresRef = FirebaseDatabase.getInstance().getReference("Scores")


        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                scoreList.clear()


                for (scoreSnapshot in snapshot.children) {
                    // Skoru al
                    val score = scoreSnapshot.getValue(Score::class.java)
                    score?.let {
                        // Skor listesine ekle
                        scoreList.add(it)
                    }
                }


                adapter.setScoreList(scoreList)
            }

            override fun onCancelled(error: DatabaseError) {

                Toast.makeText(this@ScoreBoardActivity, "Skorlar alınamadı.", Toast.LENGTH_SHORT).show()
            }
        }


        scoresRef.addValueEventListener(valueEventListener)
    }

    override fun onDestroy() {
        if (::valueEventListener.isInitialized) {
            scoresRef.removeEventListener(valueEventListener)
        }
        super.onDestroy()
    }
}
