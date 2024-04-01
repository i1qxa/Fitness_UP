package com.fitlife.atfsd.ui.rv_training

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import org.fitnessup.wjtt.R
import org.fitnessup.wjtt.domain.TrainingWithCommonData
import org.fitnessup.wjtt.presentation.sport.rv.TrainingDiffCallBack

class TrainingRVAdapter :
    ListAdapter<TrainingWithCommonData, TrainingViewHolder>(TrainingDiffCallBack()) {

    var onTrainingItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TrainingViewHolder(
            layoutInflater.inflate(
                R.layout.training_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        val item = getItem(position)
        with(holder) {
            trainingName.text = item.name
            if (item.logo != null) {
                trainingLogo.load(item.logo) {
                    transformations(RoundedCornersTransformation(20.0f))
                }
            }
            amountExercises.text =
            holder.itemView.context.getString(R.string.exercise_amount, item.amountExercises)
            totalDuration.text = item.totalTimeFormatted
            itemView.setOnClickListener {
                onTrainingItemClickListener?.invoke(item.id)
            }
        }
    }


}