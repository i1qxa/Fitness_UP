package org.fitnessup.wjtt.presentation.food.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import org.fitnessup.wjtt.R
import org.fitnessup.wjtt.data.local.food.FoodDB
import org.fitnessup.wjtt.data.remote.RecipeItemShort
import org.fitnessup.wjtt.domain.getBitmapByName
import org.fitnessup.wjtt.presentation.food.kcal_counter.rv.FoodViewHolder

class FoodDBRVAdapter : ListAdapter<FoodDB, FoodViewHolder>(FoodDBDiffCallBack()) {

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
        with(holder) {
            if (item.isLocal) {
                if (item.img.isNotEmpty()) {
                    val bmp = holder.itemView.context.getBitmapByName(item.img)
                    ivLogo.setImageBitmap(bmp)
                }
            } else {
                ivLogo.load(item.img) {
                    transformations(RoundedCornersTransformation(20.0f))
                }
            }
            tvName.text = item.name.firstCharToUpperCase()
            tvKcalValue.text = item.kcalTotal.toString()
            tvProteinValue.text = item.proteinTotal.toString()
            tvFatValue.text = item.fatTotal.toString()
            tvCarbValue.text = item.carbTotal.toString()
        }
    }
}

fun String.firstCharToUpperCase(): String {
    if (this.isNotEmpty()) {
        val firstChar = this.trim()[0].uppercaseChar()
        return "$firstChar${this.removeRange(0..1)}"
    }else return ""
}