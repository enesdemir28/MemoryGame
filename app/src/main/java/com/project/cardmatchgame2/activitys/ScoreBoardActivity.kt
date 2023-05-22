package com.project.cardmatchgame2.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.cardmatchgame2.R

class ScoreBoardActivity : AppCompatActivity() {
    private lateinit var scoreTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_board)
        val userId = "current_user" // Kullanıcının benzersiz kimliği
        val scoreRef = FirebaseDatabase.getInstance().getReference("Scores").child(userId)
        scoreRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userScore = snapshot.getValue(Int::class.java)
                    // Skoru görüntüle
                    scoreTextView.text = "Skor: $userScore"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Veri alma işlemi iptal edildiğinde yapılacak işlemleri buraya yazın
            }
        })
    }
}