package com.project.cardmatchgame2.activitys.activitys

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
        // Binding özelliği için yazılmış kodlar
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // ViewBinding özelliğini kullanarak intent eklentisiyle PlayersActivity ekranına geçmemizi sağlayan kodlar
        binding.btntek.setOnClickListener {

            var intent = Intent(this, PlayersActivity::class.java)
            startActivity(intent)
        }
        // ViewBinding özelliğini kullanarak intent eklentisiyle RegisterActivity ekranına geçmemizi sağlayan kodlar
        binding.btnregister.setOnClickListener{

           var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        // ViewBinding özelliğini kullanarak intent eklentisiyle ScoreBoardActivity ekranına geçmemizi sağlayan kodlar
        binding.btnscrbrd.setOnClickListener {
            var intent = Intent(this, ScoreBoardActivity::class.java)
            startActivity(intent)
        }
    }
}