package org.fitnessup.wjtt.presentation.sport.add_new_training.rv

import androidx.recyclerview.widget.DiffUtil
import org.fitnessup.wjtt.data.local.exercise_items.ExerciseItems

class ExerciseDiffCallBack: DiffUtil.ItemCallback<ExerciseItems>() {

    override fun areItemsTheSame(oldItem: ExerciseItems, newItem: ExerciseItems): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ExerciseItems, newItem: ExerciseItems): Boolean {
        return oldItem == newItem
    }
}