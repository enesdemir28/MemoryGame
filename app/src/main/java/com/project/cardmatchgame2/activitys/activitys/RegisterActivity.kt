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

        // Firebase veritabanı referanslarını al
        usersRef = FirebaseDatabase.getInstance().getReference("Users")
        countRef = FirebaseDatabase.getInstance().getReference("UserCount")

        binding.btnkytol.setOnClickListener {
            val ad = binding.edtadsoyad.text.toString().trim()

            if (ad.isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Geçersiz değer! Lütfen bir isim girin.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Kullanıcı isimlerini kontrol etmek için ValueEventListener kullanarak veritabanından verileri okuyoruz
            usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var isNameAvailable = true

                    // Veritabanındaki her kullanıcı için döngü
                    for (userSnapshot in snapshot.children) {
                        // Kullanıcıyı al
                        val user = userSnapshot.getValue(User::class.java)
                        if (user != null && user.ad == ad) {
                            // Girilen isim başka bir kullanıcı tarafından kullanılıyor
                            isNameAvailable = false
                            break
                        }
                    }

                    if (!isNameAvailable) {
                        // Girilen isim başka bir kullanıcı tarafından kullanılıyor
                        Toast.makeText(this@RegisterActivity, "Başka bir isim giriniz.", Toast.LENGTH_SHORT).show()
                        return
                    }

                    // Kullanıcı ismi geçerli ve kullanılabilir
                    // Kullanıcı sayısını almak için ValueEventListener kullanarak veritabanından veriyi okuyoruz
                    countRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val count = snapshot.getValue(Long::class.java) ?: 0L
                            val userId = count + 1
                            val user = User(ad, userId)

                            // Kullanıcıyı veritabanına ekle
                            usersRef.child(userId.toString()).setValue(user).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Kayıt başarılı
                                    Toast.makeText(this@RegisterActivity, "Kayıt Başarılı ", Toast.LENGTH_LONG).show()

                                    // Skorları kaydetmek için "Scores" düğümüne kullanıcıyı ekle
                                    val scoresRef = FirebaseDatabase.getInstance().getReference("Scores")
                                    scoresRef.child(userId.toString()).setValue(user).addOnCompleteListener { scoreTask ->
                                        if (scoreTask.isSuccessful) {
                                            // Kullanıcı "Scores" düğümüne eklendi
                                            // Skor kaydedildiğine dair işlemleri burada yapabilirsiniz
                                        }
                                    }
                                } else {
                                    // Kayıt başarısız
                                    val error = task.exception
                                    Toast.makeText(this@RegisterActivity, "Kayıt başarısız : ${error?.message}", Toast.LENGTH_LONG).show()
                                }
                            }
                            // Kullanıcı sayısını bir artırarak güncelle
                            countRef.setValue(count + 1)
                        }
                        override fun onCancelled(error: DatabaseError) {
                            // İstek iptal edildiğinde veya başarısız olduğunda yapılacaklar
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
