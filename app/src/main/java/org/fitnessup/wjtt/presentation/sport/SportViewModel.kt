package org.fitnessup.wjtt.presentation.sport

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.fitnessup.wjtt.data.local.FitnessUPDB

class SportViewModel(application: Application) : AndroidViewModel(application) {

    private val trainingDao = FitnessUPDB.getInstance(application).trainingsDao()

    val listOfTrainings = trainingDao.getTrainingsListShort()

}