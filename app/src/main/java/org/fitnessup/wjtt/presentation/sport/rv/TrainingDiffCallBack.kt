package org.fitnessup.wjtt.presentation.sport.rv

import androidx.recyclerview.widget.DiffUtil
import org.fitnessup.wjtt.domain.TrainingWithCommonData

class TrainingDiffCallBack:DiffUtil.ItemCallback<TrainingWithCommonData>() {

    override fun areItemsTheSame(
        oldItem: TrainingWithCommonData,
        newItem: TrainingWithCommonData
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: TrainingWithCommonData,
        newItem: TrainingWithCommonData
    ): Boolean {
        return oldItem == newItem
    }
}