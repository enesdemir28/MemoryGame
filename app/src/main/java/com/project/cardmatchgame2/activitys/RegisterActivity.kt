package com.project.cardmatchgame2.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase
import com.project.cardmatchgame2.R

import com.project.cardmatchgame2.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usersref = FirebaseDatabase.getInstance().getReference("Users")
        binding.btnkytol.setOnClickListener {
            val ad = binding.edtadsoyad.text.toString().trim()
            val user = User(ad)
            usersref.push().setValue(user)
        }

    }
}