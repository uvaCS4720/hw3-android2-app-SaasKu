package edu.nd.pmcburne.hwapp.one.model

import android.Manifest
import android.net.*
import android.content.Context
import androidx.annotation.RequiresPermission
import retrofit2.Retrofit
import android.annotation.SuppressLint

// TODO: DONT FORGET TO CITE this link properly!!!!!!!!!!!!!
//https://developer.android.com/develop/connectivity/network-ops/reading-network-state

class GameRepo(private val context: Context, private val gameDao: GameDao) {
    @SuppressLint("MissingPermission")
    suspend fun getGames(
        gender: String,
        year: String,
        month: String,
        day: String
    ): List<Game>{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val net = connectivityManager.activeNetwork
        val cap = connectivityManager.getNetworkCapabilities(net)
        val isOnline = cap?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        if (isOnline){
            val response = RetrofitInst.api
                .getGames(
                    gender,
                    year,
                    month,
                    day
                )
            val games = response.games.map { it.game.convertInfoToGame(gender)}
            gameDao.insertGames(games)

            return gameDao.getGames(gender, "$month/$day/$year")


        }

        return gameDao.getGames(gender, "$month/$day/$year")

    }
}