package com.fitlife.atfsd.ui.rv_training

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import org.fitnessup.wjtt.R

class TrainingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val trainingName = itemView.findViewById<TextView>(R.id.tvTrainingName)
    val trainingLogo = itemView.findViewById<ImageView>(R.id.trainingLogo)
    val amountExercises = itemView.findViewById<TextView>(R.id.exerciseAmount)
    val totalDuration = itemView.findViewById<TextView>(R.id.tvTotalDuration)
    val trainingCard = itemView.findViewById<MaterialCardView>(R.id.trainingCard)
}