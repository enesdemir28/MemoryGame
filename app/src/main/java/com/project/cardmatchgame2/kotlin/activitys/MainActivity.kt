package com.project.cardmatchgame2.kotlin.activitys

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.cardmatchgame2.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btntek.setOnClickListener {
            var intent = Intent(this, PlayersActivity::class.java)
            startActivity(intent)
        }
        binding.btnregister.setOnClickListener{
           var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.btnscrbrd.setOnClickListener {
            var intent = Intent(this, ScoreBoardActivity::class.java)
            startActivity(intent)
        }
    }
}