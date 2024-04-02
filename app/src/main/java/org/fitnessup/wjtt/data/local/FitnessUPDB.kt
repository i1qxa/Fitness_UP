package org.fitnessup.wjtt.data.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.fitnessup.wjtt.data.local.exercise_items.ExerciseItems
import org.fitnessup.wjtt.data.local.exercise_items.ExerciseItemsDao
import org.fitnessup.wjtt.data.local.exercise_types.ExerciseTypesDB
import org.fitnessup.wjtt.data.local.exercise_types.ExerciseTypesDao
import org.fitnessup.wjtt.data.local.treinings.TrainingsDao
import org.fitnessup.wjtt.data.local.treinings.Treinings
import kotlinx.coroutines.InternalCoroutinesApi
import org.fitnessup.wjtt.data.local.food.FoodDB
import org.fitnessup.wjtt.data.local.food.FoodDao
import org.fitnessup.wjtt.data.local.person.PersonDB
import org.fitnessup.wjtt.data.local.person.PersonDao

@Database(
    entities = [
        ExerciseItems::class,
        ExerciseTypesDB::class,
        Treinings::class,
        FoodDB::class,
        PersonDB::class
    ], exportSchema = false,
    version = 1
)
abstract class FitnessUPDB : RoomDatabase() {

    abstract fun exerciseItemsDao(): ExerciseItemsDao
    abstract fun exerciseTypesDao(): ExerciseTypesDao
    abstract fun foodDao():FoodDao
    abstract fun trainingsDao(): TrainingsDao

    abstract fun personDao():PersonDao

    companion object {
        private var INSTANCE: FitnessUPDB? = null
        private val LOCK = Any()
        private const val DB_NAME = "fitness_up_db"

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(application: Application): FitnessUPDB {
            INSTANCE?.let {
                return it
            }
            kotlinx.coroutines.internal.synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    FitnessUPDB::class.java,
                    DB_NAME
                )
                    .createFromAsset("fitness_up.db")
                    .build()
                INSTANCE = db
                return db
            }
        }
    }


}