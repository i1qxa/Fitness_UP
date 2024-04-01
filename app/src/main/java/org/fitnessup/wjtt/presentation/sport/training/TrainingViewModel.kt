package org.fitnessup.wjtt.presentation.sport.training

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import org.fitnessup.wjtt.data.local.FitnessUPDB

class TrainingViewModel(application: Application) : AndroidViewModel(application) {

    private val exerciseDao = FitnessUPDB.getInstance(application).exerciseItemsDao()
    private val trainingDao = FitnessUPDB.getInstance(application).trainingsDao()

    private val trainingIdLD = MutableLiveData<Int>()

    val exerciseList = trainingIdLD.switchMap { trainingId ->
        exerciseDao.getExercisesForTraining(trainingId)
    }

    val exerciseType = trainingIdLD.switchMap { trainingId ->
        trainingDao.getExerciseTypeIdByTrainingId(trainingId)
    }

    fun setupTrainingId(id:Int){
        trainingIdLD.value = id
    }

}