package org.fitnessup.wjtt.domain

import java.util.Calendar

fun main(){
    val currentDate = Calendar.getInstance()
    println(currentDate.timeInMillis)
    val year = currentDate.get(Calendar.YEAR)
    val month = currentDate.get(Calendar.MONTH)
    val day = currentDate.get(Calendar.DAY_OF_MONTH)
    val beginOfDay = Calendar.getInstance()
    println(beginOfDay.timeInMillis)
    beginOfDay.set(year, month,day, 0,0,0)

    println(beginOfDay.timeInMillis)
}