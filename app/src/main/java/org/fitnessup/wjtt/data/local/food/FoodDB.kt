package org.fitnessup.wjtt.data.local.food

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FoodDB(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val img:String,
    val date:String,
    val dateInMils:Long,
    val weightInGrams:Int,
    val kcalPerGram:Double,
    val proteinPerGram:Double,
    val fatPerGram:Double,
    val carbPerGram:Double,
){
    val kcalTotal:Double
        get() = kcalPerGram*weightInGrams
    val proteinTotal:Double
        get() = proteinPerGram*weightInGrams
    val fatTotal:Double
        get() = fatPerGram*weightInGrams
    val carbTotal:Double
        get() = carbPerGram*weightInGrams
}
