package edu.nd.pmcburne.hwapp.one.model

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room
/***************************************************************************************
 * REFERENCES
 * Title: Persist data with Room
 * Author: Android Developers
 * URL:https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room?authuser=2#0
 * Date: May 17, 2024
 * Software License: Apache 2 License
 * Usage: I used this to learn about Room and used it as a reference for my implementation
 ***************************************************************************************/
@Database(entities = [Game::class], version = 1, exportSchema = false)
abstract class GameDatabase: RoomDatabase() {
    abstract fun gameDao(): GameDao

    companion object {
        @Volatile
        private var inst: GameDatabase ?= null

        fun getDatabase(context: Context): GameDatabase{
            return inst ?: synchronized(lock=this){
                val instance = Room.databaseBuilder(context, GameDatabase::class.java, "game_database")
                    .build()
                    inst = instance
                    instance
            }
        }
    }

}