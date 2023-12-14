package com.populartv.app.network

import com.populartv.app.model.PopularTVListResponse
import com.populartv.app.model.TVResponseDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TVSeriesApiService {
    @GET("/3/tv/popular?language=en-US")
    suspend fun getPopularTvShow(@Query("api_key") api_key : String, @Query("page") page: Int) : PopularTVListResponse

    @GET("/3/tv/{id}")
    suspend fun getPopularTvShowDetails(@Path("id") id : String, @Query ("api_key") api_key: String) : TVResponseDetails
}