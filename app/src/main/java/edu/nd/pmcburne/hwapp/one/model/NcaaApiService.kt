package edu.nd.pmcburne.hwapp.one.model

import retrofit2.Retrofit
import retrofit2.http.*
import retrofit2.converter.gson.GsonConverterFactory

// Link Example: https://ncaa-api.henrygd.me/scoreboard/basketball-men/d1/2026/02/17
/***************************************************************************************
 * REFERENCES
 * Title:  Retrofit Documentation
 * Author: Square Inc
 * URL:https://square.github.io/retrofit/
 * Software License: Apache 2.0 License
 * Usage: I used this to learn how to use Retrofit for my project
 ***************************************************************************************/
private const val baseUrl = "https://ncaa-api.henrygd.me/"
private const val division = "d1"

interface NcaaApiService {
    @GET("scoreboard/basketball-{gender}/d1/{year}/{month}/{day}")
    suspend fun getGames(
        @Path("gender") gender: String,
        @Path("year") year: String,
        @Path("month") month: String,
        @Path("day") day : String
    ): GameResponseDetails
}

object RetrofitInst{
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create()).build()

    val api: NcaaApiService = retrofit.create(NcaaApiService::class.java)
}