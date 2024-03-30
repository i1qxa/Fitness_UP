package org.fitnessup.wjtt.presentation.kcal_counter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.launch
import org.fitnessup.wjtt.data.local.FitnessUPDB
import org.fitnessup.wjtt.data.remote.APP_ID
import org.fitnessup.wjtt.data.remote.APP_KEY
import org.fitnessup.wjtt.data.remote.ITEM_SPLITTER
import org.fitnessup.wjtt.data.remote.RecipeItemShort
import org.fitnessup.wjtt.data.remote.RecipeTranslatedSubData
import org.fitnessup.wjtt.data.remote.RetrofitService
import java.util.Calendar

class ChooseFoodViewModel(application: Application) : AndroidViewModel(application) {

    private val foodDao = FitnessUPDB.getInstance(application).foodDao()
    private val retrofit = RetrofitService.getInstance()

    val listRecipes: MutableLiveData<List<RecipeItemShort?>?> =
        MutableLiveData<List<RecipeItemShort?>?>()

    val errorRequest = MutableLiveData<Boolean>()

    val finishAddingFood = MutableLiveData<Boolean>()

    fun addFoodToDB(foodItem: RecipeItemShort, weight: Int) {
        val currentDate = Calendar.getInstance()
        val dateInMils = currentDate.timeInMillis
        val dateAsString = getCurrentDateAsYYYYMMDD(currentDate)
        viewModelScope.launch {
            foodDao.addFoodItem(foodItem.toFoodDB(weight, dateAsString, dateInMils))
            finishAddingFood.postValue(true)
        }
    }

    private fun getRecipeList(query: String) {

        viewModelScope.launch {
            listRecipes.postValue(null)
            val recipes = mutableListOf<RecipeItemShort?>()
            val recipesTranslated = mutableListOf<RecipeItemShort>()
            val response = retrofit.getRecipeResponse(
                "public", query, APP_ID, APP_KEY
            )
            if (response.isSuccessful) {
                if (response.body()?.count == 0) {
                    errorRequest.value = true
                } else {
                    errorRequest.value = false
                    var startId = 0
                    response.body()?.hits?.map {
                        recipes.add(it.recipe?.getRecipeShort(startId))
                        startId++
                    }
                    val encoded = StringBuilder()
                    recipes.forEach {
                        if (it != null) {
                            encoded.append(it.getDataForTranslate())
                            encoded.append(ITEM_SPLITTER)
                        }
                    }
                    val options =
                        TranslatorOptions.Builder().setSourceLanguage(TranslateLanguage.ENGLISH)
                            .setTargetLanguage(TranslateLanguage.RUSSIAN).build()
                    val englishGermanTranslator = Translation.getClient(options)
                    var conditions = DownloadConditions.Builder().build()
                    englishGermanTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener {
                        val translator = Translation.getClient(options)
                        translator.translate(encoded.toString())
                            .addOnSuccessListener { translatedCollection ->
                                translatedCollection.split(ITEM_SPLITTER).forEach {
                                    if (it.isNotEmpty()) {
                                        val tmpTranslatedItem =
                                            RecipeTranslatedSubData.decodeFromString(it)
                                        val recipeEnglishList =
                                            recipes.filter { it?.id == tmpTranslatedItem?.id }
                                        if (recipeEnglishList.isNotEmpty()) {
                                            val recipeEnglish = recipeEnglishList[0]
                                            if (recipeEnglish != null && tmpTranslatedItem != null) {
                                                recipesTranslated.add(
                                                    recipeEnglish.copy(
                                                        label = tmpTranslatedItem.name,
                                                        dietLabels = tmpTranslatedItem.diets,
                                                        ingredients = tmpTranslatedItem.ingredients
                                                    )
                                                )
                                            }
                                        }

                                    }

                                }
                                listRecipes.postValue(recipesTranslated)
                            }
                    }.addOnFailureListener { exception ->
                    }
                }
            }
        }

    }

    fun translateQuery(queryRussian: String) {
        val options =
            TranslatorOptions.Builder().setSourceLanguage(TranslateLanguage.RUSSIAN)
                .setTargetLanguage(TranslateLanguage.ENGLISH).build()
        val russianEnglishTranslator = Translation.getClient(options)
        var conditions = DownloadConditions.Builder().build()
        russianEnglishTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener {
            val translator = Translation.getClient(options)
            translator.translate(queryRussian)
                .addOnSuccessListener {
                    getRecipeList(it)
                }
        }
            .addOnFailureListener { exception ->
            }
    }

    fun getCurrentDateAsYYYYMMDD(calendar: Calendar): String {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val answer = StringBuilder()
        answer.apply {
            append(year)
            append("/")
            append(month)
            append("/")
            append(day)
        }
        return answer.toString()
    }

}