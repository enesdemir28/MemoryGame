package com.project.cardmatchgame2.activitys

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.project.cardmatchgame2.R


class UserRvAdapter(private val userList: List<User>) : RecyclerView.Adapter<UserRvAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_rv_item, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.bind(currentUser)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, GameScreenActivity::class.java)
            intent.putExtra("userId", currentUser.userId)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userTitleTextView: TextView = itemView.findViewById(R.id.note_title)

        fun bind(user: User) {
            userTitleTextView.text = user.ad
        }
    }
}