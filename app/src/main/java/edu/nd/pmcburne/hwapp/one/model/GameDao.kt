package edu.nd.pmcburne.hwapp.one.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.*

/***************************************************************************************
 * REFERENCES
 * Title: Accessing data using Room DAOs
 * Author: Android Developers
 * URL: https://developer.android.com/training/data-storage/room/accessing-data?utm_source=android-studio-app&utm_medium=app
 * Software License: Apache 2 License
 * Usage: I used this to find out how to set up Dao and how to use it to do SQL queries
 ***************************************************************************************/
@Dao
interface GameDao {

    @Query("SELECT * FROM game_table WHERE gender = :gender AND startDate = :date")
    suspend fun getGames(gender: String, date: String): List<Game>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<Game>)

    @Query("DELETE FROM game_table")
    suspend fun deleteEveryGame()
}