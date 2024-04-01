package org.fitnessup.wjtt.presentation.sport.add_new_training.rv

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.fitnessup.wjtt.R

class ExerciseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val logo = itemView.findViewById<ImageView>(R.id.ivExerciseLogo)
    val exerciseName = itemView.findViewById<TextView>(R.id.tvExerciseName)
    val exerciseDescription = itemView.findViewById<TextView>(R.id.tvExerciseDescription)
    val cbIsSelected = itemView.findViewById<CheckBox>(R.id.cbIsSelected)

}