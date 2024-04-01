package org.fitnessup.wjtt.data.local.exercise_items

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.fitnessup.wjtt.domain.TrainingWithCommonData

@Dao
interface ExerciseItemsDao {

    @Query("SELECT * FROM exerciseitems WHERE training_id =:trainingId")
    fun getExercisesForTraining(trainingId:Int):LiveData<List<ExerciseItems>>

    @Query("SELECT * FROM exerciseitems WHERE id =:exerciseId")
    fun getExercise(exerciseId:Int):LiveData<ExerciseItems>

    @Query("SELECT id, name, repeat as amountExercises, (repeat*duration) as totalTime, logo FROM exerciseitems WHERE name LIKE :query")
    fun getExerciseByQuery(query:String):LiveData<List<TrainingWithCommonData>>


}