package com.project.cardmatchgame2.activitys.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import com.google.firebase.database.*
import com.project.cardmatchgame2.activitys.model.User

import com.project.cardmatchgame2.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var usersRef: DatabaseReference
    private lateinit var countRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        usersRef = FirebaseDatabase.getInstance().getReference("Users")
        countRef = FirebaseDatabase.getInstance().getReference("UserCount")

        binding.btnkytol.setOnClickListener {
            val ad = binding.edtadsoyad.text.toString().trim()

            if (ad.isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Geçersiz değer! Lütfen bir isim girin.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var isNameAvailable = true


                    for (userSnapshot in snapshot.children) {

                        val user = userSnapshot.getValue(User::class.java)
                        if (user != null && user.ad == ad) {
                            isNameAvailable = false
                            break
                        }
                    }

                    if (!isNameAvailable) {

                        Toast.makeText(this@RegisterActivity, "Başka bir isim giriniz.", Toast.LENGTH_SHORT).show()
                        return
                    }



                    countRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val count = snapshot.getValue(Long::class.java) ?: 0L
                            val userId = count + 1
                            val user = User(ad, userId)


                            usersRef.child(userId.toString()).setValue(user).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this@RegisterActivity, "Kayıt Başarılı ", Toast.LENGTH_LONG).show()


                                    val scoresRef = FirebaseDatabase.getInstance().getReference("Scores")
                                    scoresRef.child(userId.toString()).setValue(user).addOnCompleteListener { scoreTask ->
                                        if (scoreTask.isSuccessful) {

                                        }
                                    }
                                } else {

                                    val error = task.exception
                                    Toast.makeText(this@RegisterActivity, "Kayıt başarısız : ${error?.message}", Toast.LENGTH_LONG).show()
                                }
                            }

                            countRef.setValue(count + 1)
                        }
                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(this@RegisterActivity, "Kullanıcı sayısı alınamadı.", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@RegisterActivity, "Veriler alınamadı.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
