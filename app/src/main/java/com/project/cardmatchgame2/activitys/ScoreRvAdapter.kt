package com.project.cardmatchgame2.activitys

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.cardmatchgame2.R

class ScoreRvAdapter(private val scoreList: List<Score>) : RecyclerView.Adapter<ScoreRvAdapter.ScoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.score_rv_item, parent, false)
        return ScoreViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val currentScore = scoreList[position]
        holder.bind(currentScore)
    }

    override fun getItemCount(): Int {
        return scoreList.size
    }

    inner class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)
        private val scoreTextView: TextView = itemView.findViewById(R.id.scoreTextView)

        fun bind(score: Score) {
            userNameTextView.text = score.userId
            scoreTextView.text = score.score.toString()
        }
    }
}