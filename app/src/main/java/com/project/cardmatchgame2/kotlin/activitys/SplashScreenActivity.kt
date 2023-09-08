package com.project.cardmatchgame2.kotlin.activitys

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.project.cardmatchgame2.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun bindingHazirla() {
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    private fun init() {
        bindingHazirla()
        initCounterDown()
    }


    fun Activity.ekranaGec(sonrakiEkran : Class<*>) {
        startActivity(Intent(this, sonrakiEkran))
        finish()
    }


    fun initCounterDown() {
        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                ekranaGec(MainActivity::class.java)
                finish()
            }
        }.start()
    }
}