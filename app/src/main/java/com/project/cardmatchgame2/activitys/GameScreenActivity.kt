package com.project.cardmatchgame2.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.project.cardmatchgame2.R
import com.project.cardmatchgame2.databinding.ActivityGameScreenBinding


class GameScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivityGameScreenBinding

    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var score: Int = 0
    private lateinit var scoreTextView: TextView
    private var indexofsingleselectedcard: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scoreTextView = binding.scoretxt
        val images = mutableListOf(R.drawable.caticon,R.drawable.foxicon,R.drawable.lionicon)
        images.addAll(images)
        images.shuffle()

        buttons = listOf(binding.imageButton1, binding.imageButton2, binding.imageButton3, binding.imageButton4,
            binding.imageButton5, binding.imageButton6
        )

       cards = buttons.indices.map{ index ->
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
        scoreTextView.text = "Skor :  $score"
    }

    private fun updateModels(position: Int) {
        val card = cards[position]

        if (indexofsingleselectedcard == null){
            restoreCards()
         indexofsingleselectedcard = position
        } else{
         checkforMatch(indexofsingleselectedcard!!,position)
            indexofsingleselectedcard = null
        }
        card.isFaceup = true

    }

    private fun restoreCards() {
        for(card in cards){
            if(!card.isMatched){
                card.isFaceup = false
            }
        }
    }

    private fun isGameFinished(): Boolean {
        return cards.all { it.isMatched }
    }

    private fun showScore() {
        scoreTextView.text = "Skor:  $score"
        Toast.makeText(this, "Oyun bitti. Skor: $score", Toast.LENGTH_SHORT).show()

    }

    private fun checkforMatch(position1: Int,position2 : Int) {
       if (cards[position1].identityfy == cards[position2].identityfy){
           Toast.makeText(this,"Eşleşti",Toast.LENGTH_SHORT).show()
           cards[position1].isMatched = true
           cards[position2].isMatched = true
           score++
           if(isGameFinished()){
               showScore()
           }
       }

    }

}

