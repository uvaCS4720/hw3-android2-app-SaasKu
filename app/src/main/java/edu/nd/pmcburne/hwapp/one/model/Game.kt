package edu.nd.pmcburne.hwapp.one.model

import androidx.room.*
import java.time.LocalDateTime

@Entity(
    tableName = "game_table",
    primaryKeys = ["id", "gender"]
)
data class Game(
    val id: String,
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
){
    fun calculateTimeRemaining(): String {
        if (currentPeriod == "HALFTIME") return "20:00"

        val parts = contestClock.split(":")
        if (parts.size != 2) return contestClock

        val minutes = parts[0].toIntOrNull() ?: 0
        val seconds = parts[1].toIntOrNull() ?: 0
        val clockSeconds = minutes * 60 + seconds

        val totalSeconds: Int
        if (gender == "men") {
            totalSeconds = when (currentPeriod) {
                "1st" -> clockSeconds + 20 * 60
                "2nd" -> clockSeconds
                else -> clockSeconds
            }
        } else {
            val periodsLeft = when (currentPeriod) {
                "1st" -> 3
                "2nd" -> 2
                "3rd" -> 1
                "4th" -> 0
                else -> 0
            }
            totalSeconds = clockSeconds + periodsLeft * 10 * 60
        }

        val remMin = totalSeconds / 60
        val remSec = totalSeconds % 60
        return "%d:%02d".format(remMin, remSec)
    }
}


