package org.fitnessup.wjtt.presentation.food.kcal_counter.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import org.fitnessup.wjtt.R
import org.fitnessup.wjtt.data.remote.RecipeItemShort

class FoodRVAdapter : ListAdapter<RecipeItemShort, FoodViewHolder>(FoodDiffCallBack()) {

    var onItemClickListener: ((RecipeItemShort) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FoodViewHolder(
            layoutInflater.inflate(
                R.layout.recipe_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(item)
        }
        with(holder) {
            ivLogo.load(item.imgSmall) {
                transformations(RoundedCornersTransformation(20.0f))
            }
            tvName.text = item.label?.firstCharToUpperCase()
        }
        with(holder){
            tvKcalValue.text = item.calories.toString()
            tvProteinValue.text = item.protein.toString()
            tvFatValue.text = item.fat.toString()
            tvCarbValue.text= item.carbs.toString()
        }
    }
}

fun String.firstCharToUpperCase():String{
    val firstChar = this.trim()[0].uppercaseChar()
    return "$firstChar${this.removeRange(0..1)}"
}