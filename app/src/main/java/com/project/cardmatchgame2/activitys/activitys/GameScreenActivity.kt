package com.project.cardmatchgame2.activitys.activitys

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.cardmatchgame2.R
import com.project.cardmatchgame2.activitys.model.MemoryCard
import com.project.cardmatchgame2.activitys.model.Score
import com.project.cardmatchgame2.databinding.ActivityGameScreenBinding


class GameScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivityGameScreenBinding
    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var score: Int = 0
    private lateinit var scoreTextView: TextView
    private var indexOfSingleSelectedCard: Int? = null
    private var gameFinishedDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra("userId")

        scoreTextView = binding.scoretxt


        val images = mutableListOf(R.drawable.caticon, R.drawable.foxicon, R.drawable.lionicon)
        images.addAll(images)
        images.shuffle()


        buttons = listOf(
            binding.imageButton1, binding.imageButton2, binding.imageButton3, binding.imageButton4,
            binding.imageButton5, binding.imageButton6
        )


        cards = buttons.indices.map { index ->
            MemoryCard(false, false, images[index])
        }
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                updateModels(index)
                updateViews()
            }
        }
    }


    private fun updateViews() {
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            if (card.isFaceup) {
                button.setImageResource(card.identityfy)
            } else {
                button.setImageResource(R.drawable.questmark)
            }
        }
        scoreTextView.text = "Skor : $score"
    }


    private fun updateModels(position: Int) {
        val card = cards[position]

        if (indexOfSingleSelectedCard == null) {
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceup = true
    }


    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceup = false
            }
        }
    }


    private fun isGameFinished(): Boolean {
        val isFinished = cards.all { it.isMatched }
        if (isFinished) {
            showGameFinishedDialog()
        }
        return isFinished
    }


    private fun checkForMatch(position1: Int, position2: Int) {
        if (cards[position1].identityfy == cards[position2].identityfy) {
            cards[position1].isMatched = true
            cards[position2].isMatched = true
            score++
            if (isGameFinished()) {
                showScore()
            }
        }
    }


    private fun showScore() {
        val userId = intent.getLongExtra("userId", 0L)

        if (userId != 0L) {
            val scoresRef = FirebaseDatabase.getInstance().getReference("Scores")

            scoresRef.child(userId.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val scoreData = snapshot.getValue(Score::class.java)
                    if (scoreData != null) {
                        val previousScore = scoreData.score ?: 0
                        val scoreText = scoreTextView.text.toString()
                        val scoreString = scoreText.replace(Regex("[^\\d]"), "")
                        val currentScore = scoreString.toInt()
                        val totalScore = previousScore + currentScore

                        val updatedScoreMap = HashMap<String, Any>()
                        updatedScoreMap["ad"] = scoreData.ad
                        updatedScoreMap["score"] = totalScore

                        scoresRef.child(userId.toString()).updateChildren(updatedScoreMap)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {

                                    Toast.makeText(
                                        this@GameScreenActivity,
                                        "Oyun bitti. Skor: $score",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    score = score
                                    scoreTextView.text = "Skor : $score"
                                }
                            }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

    private fun showGameFinishedDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Oyun Bitti")
        builder.setMessage("Skor:  $score ")
        builder.setPositiveButton(
            "Tekrar Dene"
        ) { dialog, which ->
            restartGame()
        }
        builder.setNegativeButton(
            "Devam Et"
        ) { dialog, which ->
            goToMainActivity()
        }
        val gameFinishedDialog = builder.create()
        gameFinishedDialog.show()
    }


    private fun restartGame() {
        score = 0
        indexOfSingleSelectedCard = null

        for (card in cards) {
            card.isFaceup = false
            card.isMatched = false
        }


        val images = mutableListOf(R.drawable.caticon, R.drawable.foxicon, R.drawable.lionicon)
        images.addAll(images)
        images.shuffle()

        cards = buttons.indices.map { index ->
            MemoryCard(false, false, images[index])
        }

        updateViews()
    }


    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Çıkış")
        builder.setMessage("Oyundan çıkmak istediğinize emin misiniz?")
        builder.setPositiveButton("Devam Et") { dialog, which ->

            dialog.dismiss()
        }
        builder.setNegativeButton("Çıkış Yap") { dialog, which ->

            goToMainActivity()
        }
        builder.setCancelable(false)
        val alertDialog = builder.create()
        alertDialog.show()
    }
}

