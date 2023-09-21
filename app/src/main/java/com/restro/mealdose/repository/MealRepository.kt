package com.restro.mealdose.repository

import com.app.compose_structure.data.local.ISharedPreferences
import com.app.compose_structure.data.remote.IBaseRemoteService
import com.app.compose_structure.di.IoDispatcher
import com.google.gson.Gson
import com.restro.mealdose.model.ResCategoryList
import com.restro.mealdose.model.ResMealDetail
import com.restro.mealdose.model.ResMealList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.restro.mealdose.common.Result

class MealRepository @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val sharedPreferences: ISharedPreferences,
    private val remoteDataSource: IBaseRemoteService,
) {

    private val gson: Gson = Gson()

    /*override suspend fun storeUserSetting(userModel: UserDataModel) {
        sharedPreferences.storeValue(PreferenceKey.KEY_USER_SETTING, gson.toJson(userModel))
    }

    override suspend fun getUserSetting(): UserDataModel {
        val data =
            sharedPreferences.getValue(PreferenceKey.KEY_USER_SETTING, gson.toJson(UserDataModel()))
        return gson.fromJson(data, UserDataModel::class.java)
    }*/

    suspend fun getCategoryList(): Result<ResCategoryList?> = withContext(dispatcher){
        remoteDataSource.getCategoryList()
    }

    suspend fun getMealListByCategory(
        categoryName: String
    ): Result<ResMealList?> = withContext(dispatcher){
        remoteDataSource.getMealListByCategory(categoryName)
    }

    suspend fun getMealDetail(
        mealId: String
    ): Result<ResMealDetail?> = withContext(dispatcher){
        remoteDataSource.getMealDetail(mealId)
    }

    suspend fun getRandomMeal(): Result<ResMealDetail?> = withContext(dispatcher){
        remoteDataSource.getRandomMeal()
    }
}