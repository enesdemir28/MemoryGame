package com.project.cardmatchgame2.activitys

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.project.cardmatchgame2.R
import com.project.cardmatchgame2.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    // View binding için gerekli ayarlamaları yapar
    private fun bindingHazirla() {
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // Gerekli başlangıç işlemlerini gerçekleştirir
    private fun init() {
        bindingHazirla()
        initCounterDown()
    }

    // Bir sonraki ekrana geçiş işlemini gerçekleştirir
    fun Activity.ekranaGec(sonrakiEkran : Class<*>) {
        startActivity(Intent(this, sonrakiEkran))
        finish()
    }

    // Geri sayım başlatma ve sonrasında bir sonraki ekrana geçiş işlemini gerçekleştirir
    fun initCounterDown() {
        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Her saniye gerçekleştiğinde yapılacak işlemler
            }

            override fun onFinish() {
                // Geri sayım tamamlandığında yapılacak işlemler
                ekranaGec(MainActivity::class.java)
                finish()
            }
        }.start()
    }
}