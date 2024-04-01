package org.fitnessup.wjtt.presentation.sport.add_new_training.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import org.fitnessup.wjtt.R
import org.fitnessup.wjtt.data.local.exercise_items.ExerciseItems
import org.fitnessup.wjtt.domain.getBitmapByName

class ExerciseRVAdapter:
    ListAdapter<ExerciseItems, ExerciseViewHolder>(ExerciseDiffCallBack()) {

    var onExerciseItemClickListener: ((ExerciseItems) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExerciseViewHolder(
            layoutInflater.inflate(
                R.layout.exercise_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val item = getItem(position)
        with(holder){
            if (item.isLocalCreated==true){
                val img = holder.itemView.context.getBitmapByName(item.logo)
                logo.load(img) {
                    transformations(RoundedCornersTransformation(20.0f))
                }
            }else{
                logo.load(item.logo) {
                    transformations(RoundedCornersTransformation(20.0f))
                }
            }
            exerciseName.text = item.name
            exerciseDescription.text = item.description
            cbIsSelected.isSelected=item.isSelected
            itemView.setOnClickListener {
                onExerciseItemClickListener?.invoke(item)
            }
        }
    }
}