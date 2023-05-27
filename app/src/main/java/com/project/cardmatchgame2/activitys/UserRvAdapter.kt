package com.project.cardmatchgame2.activitys

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.project.cardmatchgame2.R

class UserRvAdapter(private var userList: List<User>) : RecyclerView.Adapter<UserRvAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        // ViewHolder'ı oluştururken layout dosyasını kullanır
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_rv_item, parent, false)
        return UserViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        // Belirli bir pozisyondaki kullanıcıyı ViewHolder'a bağlar
        val currentUser = userList[position]
        holder.bind(currentUser)
    }
    override fun getItemCount(): Int {
        // Kullanıcı listesinin boyutunu döndürür
        return userList.size
    }
    fun setUserList(userList: List<User>) {
        // Kullanıcı listesini günceller ve RecyclerView'e bildirir
        this.userList = userList
        notifyDataSetChanged()
    }
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userTitleTextView: TextView = itemView.findViewById(R.id.note_title)
        fun bind(user: User) {
            // ViewHolder'ın verileri kullanıcıyla günceller
            userTitleTextView.text = user.ad
            // Öğeye tıklama olayını işler ve GameScreenActivity'ye geçiş yapar
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, GameScreenActivity::class.java)
                intent.putExtra("userId", user.userId)
                itemView.context.startActivity(intent)
            }
        }
    }
}