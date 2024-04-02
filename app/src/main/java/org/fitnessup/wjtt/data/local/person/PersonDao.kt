package org.fitnessup.wjtt.data.local.person

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.fitnessup.wjtt.domain.ChartData

@Dao
interface PersonDao {

    @Query("SELECT * FROM persondb ORDER BY dateInMils DESC")
    fun getLastPersonData():LiveData<PersonDB>

    @Query("SELECT date as date, weight as value FROM PersonDB ORDER BY dateInMils")
    fun getPersonDataForChart():LiveData<List<ChartData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePersonData(person:PersonDB)

}