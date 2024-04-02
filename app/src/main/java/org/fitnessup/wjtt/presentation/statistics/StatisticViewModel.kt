package org.fitnessup.wjtt.presentation.statistics

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import org.fitnessup.wjtt.data.local.FitnessUPDB

class StatisticViewModel(application: Application) : AndroidViewModel(application) {

    private val personDao = FitnessUPDB.getInstance(application).personDao()
    private val foodDao = FitnessUPDB.getInstance(application).foodDao()

    val selectedChart = MutableLiveData<Int>(1)

    val chartLD = selectedChart.switchMap {
        if (it==1){
            personDao.getPersonDataForChart()
        }else{
            foodDao.getFoodDataForChart()
        }
    }

    fun setSelectedChart(type:Int){
        selectedChart.value = type
    }

}