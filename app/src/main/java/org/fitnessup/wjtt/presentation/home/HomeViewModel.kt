package org.fitnessup.wjtt.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.fitnessup.wjtt.data.local.FitnessUPDB

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val personDao = FitnessUPDB.getInstance(application).personDao()

    val personInfoLD = personDao.getLastPersonData()

}