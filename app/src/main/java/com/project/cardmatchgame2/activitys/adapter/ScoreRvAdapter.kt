package com.project.cardmatchgame2.activitys.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.cardmatchgame2.R
import com.project.cardmatchgame2.activitys.model.Score

class ScoreRvAdapter(private var scoreList: List<Score>) : RecyclerView.Adapter<ScoreViewHolder>() {

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
    fun setScoreList(scoreList: List<Score>) {
        this.scoreList = scoreList
        notifyDataSetChanged()
    }
}
class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)
    private val scoreTextView: TextView = itemView.findViewById(R.id.scoreTextView)

    fun bind(score: Score) {
        userNameTextView.text = score.ad
        scoreTextView.text = score.score.toString()
    }
}
