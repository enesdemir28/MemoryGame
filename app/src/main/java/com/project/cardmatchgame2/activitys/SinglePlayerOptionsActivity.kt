package com.project.cardmatchgame2.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.project.cardmatchgame2.databinding.ActivitySinglePlayerOptionsBinding

class SinglePlayerOptionsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySinglePlayerOptionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySinglePlayerOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnalt.setOnClickListener {
            var intent = Intent(this, GameScreenActivity::class.java)
            startActivity(intent)
        }


    }
}