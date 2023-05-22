package com.project.cardmatchgame2.activitys

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.inflate
import com.project.cardmatchgame2.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Binding özelliği için yazılmış kodlar
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // ViewBinding özelliğini kullanarak intent eklentisiyle Whoplays ekranına geçmemizi sağlayan kodlar
        binding.btntek.setOnClickListener {

            var intent = Intent(this, SinglePlayerOptionsActivity::class.java)

            startActivity(intent)
        }
        binding.btnregister.setOnClickListener{

           var intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

    }



}