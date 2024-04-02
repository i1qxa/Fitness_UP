package org.fitnessup.wjtt.presentation.person

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.fitnessup.wjtt.data.local.FitnessUPDB
import org.fitnessup.wjtt.data.local.person.PersonDB
import java.util.Calendar

class PersonViewModel(application: Application) : AndroidViewModel(application) {
    private val personDao = FitnessUPDB.getInstance(application).personDao()

    val personInfoLD = personDao.getLastPersonData()

    fun updatePersonData(height: String, weight: String) {
        val heightDouble = if (height.isEmpty()) 0.0
        else height.toDouble()
        val weightDouble = if (weight.isEmpty()) 0.0
        else weight.toDouble()
        val calendar = Calendar.getInstance()
        val dateInMils = calendar.timeInMillis
        val date = getCurrentDateAsYYYYMMDD(calendar)
        val personDB = PersonDB(
            0,
            heightDouble,
            weightDouble,
            date,
            dateInMils,
        )
        viewModelScope.launch {
            personDao.updatePersonData(personDB)
        }
    }

    private fun getCurrentDateAsYYYYMMDD(calendar: Calendar): String {
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