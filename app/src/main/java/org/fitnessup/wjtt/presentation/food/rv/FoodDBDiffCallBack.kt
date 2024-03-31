package org.fitnessup.wjtt.presentation.food.rv

import androidx.recyclerview.widget.DiffUtil
import org.fitnessup.wjtt.data.local.food.FoodDB
import org.fitnessup.wjtt.data.remote.RecipeItemShort

class FoodDBDiffCallBack:DiffUtil.ItemCallback<FoodDB>() {

    override fun areItemsTheSame(oldItem: FoodDB, newItem: FoodDB): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FoodDB, newItem: FoodDB): Boolean {
        return oldItem == newItem
    }
}