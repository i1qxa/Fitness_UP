package org.fitnessup.wjtt.presentation.food

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.fitnessup.wjtt.data.local.FitnessUPDB
import java.util.Calendar

class FoodViewModel(application: Application) : AndroidViewModel(application) {

    private val foodDao = FitnessUPDB.getInstance(application).foodDao()

    val listOfFood = foodDao.getFoodListByDay(getCurrentDateAsYYYYMMDD())

    private fun getCurrentDateAsYYYYMMDD(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val answer = StringBuilder()
        answer.apply {
            append(year)
            append("/")
            append(month+1)
            append("/")
            append(day)
        }
        return answer.toString()
    }

}