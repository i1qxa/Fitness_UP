package org.fitnessup.wjtt.presentation.sport.instruction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import org.fitnessup.wjtt.data.local.FitnessUPDB

class VideoInstructionViewModel(application: Application):AndroidViewModel(application) {

    private val exerciseDao = FitnessUPDB.getInstance(application).exerciseItemsDao()

    private val exerciseIdLD = MutableLiveData<Int>()

    val exerciseLD = exerciseIdLD.switchMap { exerciseId ->
        exerciseDao.getExercise(exerciseId)
    }

    fun setupExerciseId(id:Int){
        exerciseIdLD.value = id
    }

}