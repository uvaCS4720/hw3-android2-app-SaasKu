package edu.nd.pmcburne.hwapp.one.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.*

//https://developer.android.com/training/data-storage/room/accessing-data

@Dao
interface GameDao {

    @Query("SELECT * FROM game_table WHERE gender = :gender AND startDate = :date")
    suspend fun getGames(gender: String, date: String): List<Game>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<Game>)

    @Query("DELETE FROM game_table")
    suspend fun deleteEveryGame()
}