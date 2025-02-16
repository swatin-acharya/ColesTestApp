package com.example.colestestapp.repositories

import com.example.colestestapp.data.datasources.MainDataRemoteSource
import com.example.colestestapp.models.Recipe
import com.example.colestestapp.error.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainDataRepository @Inject constructor(
    private val mainDataRemoteSource: MainDataRemoteSource
) {

    suspend fun getMainData(): Flow<ApiResult<List<Recipe>>> {
        return mainDataRemoteSource.fetchMainData()
    }
}