package com.project.cardmatchgame2.activitys.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.cardmatchgame2.R
import com.project.cardmatchgame2.activitys.model.Score

class ScoreRvAdapter(private var scoreList: List<Score>) : RecyclerView.Adapter<ScoreViewHolder>() {

    // Yeni bir ViewHolder oluşturmak için kullanılır
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        // R.layout.score_rv_item layout'unu kullanarak yeni bir View oluştur
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.score_rv_item, parent, false)
        return ScoreViewHolder(itemView)
    }

    // ViewHolder'ı veriyle bağlamak için kullanılır
    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        // Belirli bir pozisyondaki skoru al
        val currentScore = scoreList[position]
        // ViewHolder'ı belirli skorla bağla
        holder.bind(currentScore)
    }
    // Listede kaç öğe olduğunu döndürür
    override fun getItemCount(): Int {
        return scoreList.size
    }
    // Skor listesini güncellemek için kullanılır
    fun setScoreList(scoreList: List<Score>) {
        this.scoreList = scoreList
        notifyDataSetChanged()
    }
}
class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)
    private val scoreTextView: TextView = itemView.findViewById(R.id.scoreTextView)

    // ViewHolder'ı veriyle bağlamak için kullanılır
    fun bind(score: Score) {
        // Gerekli görünümlere verileri yerleştir
        userNameTextView.text = score.ad
        scoreTextView.text = score.score.toString()
    }
}
