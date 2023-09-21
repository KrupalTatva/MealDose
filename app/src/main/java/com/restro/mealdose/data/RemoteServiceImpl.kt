package com.app.compose_structure.data.remote

import com.restro.mealdose.common.Result
import com.restro.mealdose.model.ResCategoryList
import com.restro.mealdose.model.ResMealDetail
import com.restro.mealdose.model.ResMealList
import retrofit2.Response
import javax.inject.Inject

class RemoteServiceImpl @Inject constructor(
    private val api: ApiService
) : IBaseRemoteService {

    private suspend fun <T : Any> apiRequest(apiFunc: suspend () -> Response<T?>): Result<T?> =
        try {
            var result = apiFunc.invoke()
            if (result.isSuccessful) {
                Result.Success(result.body())
            } else {
                Result.Error(java.lang.Exception(result.message()))
            }
        } catch (exception: Exception) {
            Result.Error(exception)
        }

    override suspend fun getCategoryList(): Result<ResCategoryList?> {
        val result = apiRequest {
            api.getCategoryList()
        }

        return if (result is Result.Success) {
            if (result.data != null)
                Result.Success(result.data)
            else
                Result.Error((result as Result.Error).exception)
        } else {
            Result.Error((result as Result.Error).exception)
        }
    }

    override suspend fun getMealListByCategory(categoryName: String): Result<ResMealList?> {
        val result = apiRequest {
            api.getMealListByCategory(categoryName)
        }

        return if (result is Result.Success) {
            if (result.data != null)
                Result.Success(result.data)
            else
                Result.Error((result as Result.Error).exception)
        } else {
            Result.Error((result as Result.Error).exception)
        }
    }

    override suspend fun getMealDetail(mealId: String): Result<ResMealDetail?> {
        val result = apiRequest {
            api.getMealDetail(mealId)
        }

        return if (result is Result.Success) {
            if (result.data != null)
                Result.Success(result.data)
            else
                Result.Error((result as Result.Error).exception)
        } else {
            Result.Error((result as Result.Error).exception)
        }
    }

    override suspend fun getRandomMeal(): Result<ResMealDetail?> {
        val result = apiRequest {
            api.getRandomMeal()
        }

        return if (result is Result.Success) {
            if (result.data != null)
                Result.Success(result.data)
            else
                Result.Error((result as Result.Error).exception)
        } else {
            Result.Error((result as Result.Error).exception)
        }
    }


    /*override suspend fun getUserOwnerList(
        customerId: String,
        username: String
    ): Result<List<UserOwnerListModel>> {
        val result = apiRequest {
            api.fetchUserOwnerList(
                UserOwnerListRequest(
                    customerId, username
                )
            )
        }
        return if (result is Result.Success) {
            Result.Success(result.data?.map {
                UserOwnerListModel(
                    customerName = it?.custName ?: "",
                    customerId = it?.custId ?: "",
                    userId = it?.userId ?: "",
                )
            } ?: emptyList())
        } else {
            Result.Error((result as Result.Error).exception)
        }
    }*/

}