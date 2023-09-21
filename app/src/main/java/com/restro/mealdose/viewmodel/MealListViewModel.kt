package com.restro.mealdose.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.restro.mealdose.MealListRoute
import com.restro.mealdose.common.Result
import com.restro.mealdose.model.CategoriesItem
import com.restro.mealdose.model.MealsDetailI
import com.restro.mealdose.model.MealsItem
import com.restro.mealdose.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val mealRepository: MealRepository
) : ViewModel(){

    var mealList by mutableStateOf(emptyList<MealsItem?>())
        private set
    var loading by mutableStateOf(false)
        private set
    var error by mutableStateOf("")
        private set

    init {
        savedStateHandle.get<String>(MealListRoute.PARAM)?.let { getMealList(it) }
    }

    fun getMealList(categoryName : String){
        viewModelScope.launch {
            loading = true
            val result = mealRepository.getMealListByCategory(categoryName)
            if(result is Result.Success){
                loading = false
                mealList = result.data?.meals ?: emptyList<MealsItem>()
            }else {
                loading = false
                error = (result as Result.Error).exception?.message ?: "Something went wrong"
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