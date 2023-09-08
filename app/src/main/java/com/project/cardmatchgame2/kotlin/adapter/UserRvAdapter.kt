package com.project.cardmatchgame2.kotlin.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.project.cardmatchgame2.R
import com.project.cardmatchgame2.kotlin.model.User
import com.project.cardmatchgame2.kotlin.activitys.GameScreenActivity

class UserRvAdapter(private var userList: List<User>) : RecyclerView.Adapter<UserRvAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_rv_item, parent, false)
        return UserViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.bind(currentUser)
    }
    override fun getItemCount(): Int {
        return userList.size
    }
    fun setUserList(userList: List<User>) {
        this.userList = userList
        notifyDataSetChanged()
    }
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userTitleTextView: TextView = itemView.findViewById(R.id.note_title)
        fun bind(user: User) {
            userTitleTextView.text = user.ad
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, GameScreenActivity::class.java)
                intent.putExtra("userId", user.userId)
                itemView.context.startActivity(intent)
            }
        }
    }
}