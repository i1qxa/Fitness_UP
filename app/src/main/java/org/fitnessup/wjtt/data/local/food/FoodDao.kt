package org.fitnessup.wjtt.data.local.food

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.fitnessup.wjtt.domain.FoodCommonInfo

@Dao
interface FoodDao {

    @Query("SELECT * FROM fooddb WHERE date =:day")
    fun getFoodListByDay(day:String):LiveData<List<FoodDB>>

    @Query("SELECT SUM(kcalPerGram*weightInGrams) as kcal, SUM(proteinPerGram*weightInGrams) as protein, " +
            "SUM(fatPerGram*weightInGrams) as fat, SUM(carbPerGram*weightInGrams) " +
            "as carb, date FROM fooddb WHERE dateInMils>:startDay AND dateInMils<:endDay GROUP BY date")
    fun getFoodCommonInfoForPeriod(startDay:Long, endDay:Long):LiveData<List<FoodCommonInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFoodItem(foodItem:FoodDB)

}