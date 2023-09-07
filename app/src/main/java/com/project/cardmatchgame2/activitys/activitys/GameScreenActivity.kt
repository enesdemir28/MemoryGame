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

    // ImageButton listesi
    private lateinit var buttons: List<ImageButton>

    // Kart listesi
    private lateinit var cards: List<MemoryCard>

    // Skor tutucu
    private var score: Int = 0

    // Skor TextView
    private lateinit var scoreTextView: TextView

    // Seçili kartın index değeri
    private var indexOfSingleSelectedCard: Int? = null

    // Oyun tamamlandığında gösterilecek AlertDialog
    private var gameFinishedDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra("userId")

        scoreTextView = binding.scoretxt

        // Resimlerin listesi
        val images = mutableListOf(R.drawable.caticon, R.drawable.foxicon, R.drawable.lionicon)
        images.addAll(images)
        images.shuffle()

        // ImageButton listesini oluştur
        buttons = listOf(
            binding.imageButton1, binding.imageButton2, binding.imageButton3, binding.imageButton4,
            binding.imageButton5, binding.imageButton6
        )

        // Kart listesini oluştur ve ImageButton'lara tıklama dinleyicisi ekle
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

    // Görünümü güncelle
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

    // Modelleri güncelle
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

    // Kartları geri yükle
    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceup = false
            }
        }
    }

    // Oyun tamamlandı mı kontrol et
    private fun isGameFinished(): Boolean {
        val isFinished = cards.all { it.isMatched }
        if (isFinished) {
            showGameFinishedDialog()
        }
        return isFinished
    }

    // Eşleşme kontrolü yap
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

    // Skoru göster
    private fun showScore() {
        val userId = intent.getLongExtra("userId", 0L)
        // Skoru Firebase Realtime Database'e kaydet
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
                                    // Skor başarıyla kaydedildi, gerekli güncelleme işlemlerini burada yapabilirsiniz
                                    Toast.makeText(
                                        this@GameScreenActivity,
                                        "Oyun bitti. Skor: $score",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    score = score // Oyundaki skoru güncelle
                                    scoreTextView.text = "Skor : $score" // Skoru güncelleyi göster
                                } else {
                                    // Skor kaydetme işlemi başarısız oldu, hata mesajını göstermek veya gerekli işlemleri yapmak için burayı kullanabilirsiniz
                                }
                            }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // İstek iptal edildiğinde veya başarısız olduğunda yapılacaklar
                }
            })
        }
    }

    // Oyun tamamlandığında gösterilecek AlertDialog'u oluştur
    private fun showGameFinishedDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Oyun Bitti")
        builder.setMessage("Skor:  $score ")
        builder.setPositiveButton(
            "Tekrar Dene"
        ) { dialog, which -> // Tekrar Dene butonuna tıklandığında oyunu yeniden başlat
            restartGame()
        }
        builder.setNegativeButton(
            "Devam Et"
        ) { dialog, which -> // Devam Et butonuna tıklandığında MainActivity'e git
            goToMainActivity()
        }
        val gameFinishedDialog = builder.create()
        gameFinishedDialog.show()
    }

    // Oyunu yeniden başlat
    private fun restartGame() {
        score = 0 // Skoru sıfırla
        indexOfSingleSelectedCard = null // Seçilen kartın index değerini sıfırla

        // Tüm kartları yeniden sıfırla
        for (card in cards) {
            card.isFaceup = false
            card.isMatched = false
        }

        // Resimlerin listesi
        val images = mutableListOf(R.drawable.caticon, R.drawable.foxicon, R.drawable.lionicon)
        images.addAll(images)
        images.shuffle()
        // Kart listesini oluştur
        cards = buttons.indices.map { index ->
            MemoryCard(false, false, images[index])
        }

        updateViews() // Görünümü güncelle
    }

    // MainActivity'e git
    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Geri tuşuna basıldığında AlertDialog'u göster
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Çıkış")
        builder.setMessage("Oyundan çıkmak istediğinize emin misiniz?")
        builder.setPositiveButton("Devam Et") { dialog, which ->
            // Devam Et butonuna tıklandığında oyun devam etsin
            dialog.dismiss()
        }
        builder.setNegativeButton("Çıkış Yap") { dialog, which ->
            // Çıkış Yap butonuna tıklandığında MainActivity'e git
            goToMainActivity()
        }
        builder.setCancelable(false) // Geri tuşuna basıldığında diyalogun kapanmasını engelle

        val alertDialog = builder.create()
        alertDialog.show()
    }
}

