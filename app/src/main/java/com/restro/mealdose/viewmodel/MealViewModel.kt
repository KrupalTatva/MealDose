package com.restro.mealdose.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.restro.mealdose.common.Result
import com.restro.mealdose.model.MealsDetailI
import com.restro.mealdose.model.MealsItem
import com.restro.mealdose.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val mealRepository: MealRepository
) : ViewModel() {


    var mealsDetailI by mutableStateOf<MealsDetailI?>(null)
        private set
    var loading by mutableStateOf(false)
        private set
    var error by mutableStateOf("")
        private set

    fun getMeal(id : String ){
        viewModelScope.launch {
            loading = true
            val result = mealRepository.getMealDetail(id)

            if(result is Result.Success){
                loading = false
                mealsDetailI = result.data?.meals?.get(0) ?: null
            }else{
                loading = false
                setErrorMessage((result as Result.Error).exception?.message ?: "Something went wrong")
            }
        }
    }

    fun getRandomMeal(){
        viewModelScope.launch {
            loading = true
            val result = mealRepository.getRandomMeal()

            if(result is Result.Success){
                loading = false
                mealsDetailI = result.data?.meals?.get(0) ?: null
            }else{
                loading = false
                setErrorMessage((result as Result.Error).exception?.message ?: "Something went wrong")
            }
        }
    }


    fun revertLoading(){
        loading = !loading
    }

    fun clearError(){
        error = ""
    }

    fun setErrorMessage(message: String){
        error = message
    }
}