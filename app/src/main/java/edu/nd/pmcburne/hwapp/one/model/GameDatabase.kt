package edu.nd.pmcburne.hwapp.one.model

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room

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