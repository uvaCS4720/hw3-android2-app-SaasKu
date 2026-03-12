package edu.nd.pmcburne.hwapp.one.model

import androidx.room.*
import java.time.LocalDateTime
//TODO: PLEASE DON"T FORGET TO IMPLEMENT API RATE LIMITS
// 5 requests per second
// https://ncaa-api.henrygd.me/openapi
// THE APPLICATION WILL LOOK WEIRD IF YOU GET RATE LIMITED

//TODO: Cite Room
// https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room?authuser=2#0

@Entity(tableName = "game_table")
data class Game(
    @PrimaryKey val id: String,
    val home: String,
    val away: String,
    val homeScore: Int?,
    val awayScore: Int?,
    val homeWon: Boolean,
    val awayWon: Boolean,
    val gameState: String,
    val startTime: String,
    val startDate: String,
    val currentPeriod: String,
    val contestClock: String,
    val gender: String,
)
