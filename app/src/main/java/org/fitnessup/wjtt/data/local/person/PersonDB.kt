package org.fitnessup.wjtt.data.local.person

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val height: Double,
    val weight: Double,
    val date: String,
    val dateInMils: Long,
) {
    val bmi: Int
        get() {
            return if (height <= 0) 0
            else {
                (weight / (height * height)).toInt()
            }
        }
}
