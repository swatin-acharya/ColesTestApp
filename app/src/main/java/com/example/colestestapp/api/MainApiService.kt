package com.example.colestestapp.api

import android.content.res.AssetManager
import com.example.colestestapp.models.MainDataModel
import com.example.colestestapp.error.ApiResult
import com.example.colestestapp.error.error_message_incorrect_data
import com.example.colestestapp.error.error_message_no_file
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.flow
import java.io.IOException


/** Pretending the data comes from remote source rather than a local json file for demonstration
purposes, normally this would be an api call*/
class MainApiService (private val assetManager: AssetManager){

    suspend fun getData() = flow {
        emit(ApiResult.Loading(true))
        try {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val file = "recipesSample.json"
            val outerJsonObjString = assetManager.open(file).bufferedReader().use{ it.readText()}
            val mainType = Types.newParameterizedType(MainDataModel::class.java, MainDataModel::class.java)
            val adapter: JsonAdapter<MainDataModel> = moshi.adapter(mainType)
            val mainDataModel = adapter.fromJson(outerJsonObjString)
            if (mainDataModel == null) { emit(ApiResult.Error(error_message_incorrect_data))}
            else emit(ApiResult.Success(mainDataModel.recipes))
        } catch (ex: IOException) {
            emit(ApiResult.Error(error_message_no_file))
        }
    }
}