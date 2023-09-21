package com.app.compose_structure.data.remote

import com.restro.mealdose.common.Result
import com.restro.mealdose.model.ResCategoryList
import com.restro.mealdose.model.ResMealDetail
import com.restro.mealdose.model.ResMealList

interface IBaseRemoteService {

    suspend fun getCategoryList(): Result<ResCategoryList?>

    suspend fun getMealListByCategory(
        categoryName: String
    ): Result<ResMealList?>

    suspend fun getMealDetail(
        mealId: String
    ): Result<ResMealDetail?>

    suspend fun getRandomMeal(): Result<ResMealDetail?>
}