package com.app.compose_structure.data.remote

import com.restro.mealdose.model.ResCategoryList
import com.restro.mealdose.model.ResMealDetail
import com.restro.mealdose.model.ResMealList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("categories.php")
    suspend fun getCategoryList(): Response<ResCategoryList?>

    @GET("filter.php")
    suspend fun getMealListByCategory(
        @Query("c") categoryName: String
    ): Response<ResMealList?>

    @GET("lookup.php")
    suspend fun getMealDetail(
        @Query("i") mealId: String
    ): Response<ResMealDetail?>

    @GET("random.php")
    suspend fun getRandomMeal(): Response<ResMealDetail?>
}