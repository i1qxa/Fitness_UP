package org.fitnessup.wjtt.presentation.food.kcal_counter.rv

import androidx.recyclerview.widget.DiffUtil
import org.fitnessup.wjtt.data.remote.RecipeItemShort

class FoodDiffCallBack:DiffUtil.ItemCallback<RecipeItemShort>() {

    override fun areItemsTheSame(oldItem: RecipeItemShort, newItem: RecipeItemShort): Boolean {
        return oldItem.label == newItem.label
    }

    override fun areContentsTheSame(oldItem: RecipeItemShort, newItem: RecipeItemShort): Boolean {
        return oldItem == newItem
    }
}