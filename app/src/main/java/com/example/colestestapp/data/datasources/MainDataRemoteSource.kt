package com.example.colestestapp.data.datasources

import com.example.colestestapp.api.MainApiService
import com.example.colestestapp.models.Recipe
import com.example.colestestapp.error.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/** Pretending data comes from remote source for demonstration purpose */
class MainDataRemoteSource @Inject constructor(private val mainApiService: MainApiService) {

    suspend fun fetchMainData(): Flow<ApiResult<List<Recipe>>> {
        return mainApiService.getData()
    }
}