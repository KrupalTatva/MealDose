package com.restro.mealdose.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.restro.mealdose.common.Result
import com.restro.mealdose.model.CategoriesItem
import com.restro.mealdose.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.security.PrivateKey
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val mealRepository: MealRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    var categoryList by mutableStateOf(emptyList<CategoriesItem?>())
        private set
    var loading by mutableStateOf(false)
        private set
    var error by mutableStateOf("")
        private set

    init {
        getCategoryList()
    }

    fun getCategoryList(){
        viewModelScope.launch {
            loading = true
            val result = mealRepository.getCategoryList()
            if(result is Result.Success){
                loading = false
                categoryList = result.data?.categories ?: emptyList<CategoriesItem>()
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

