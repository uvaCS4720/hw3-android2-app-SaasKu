package edu.nd.pmcburne.hwapp.one.model

import java.time.LocalDateTime
//TODO: PLEASE DON"T FORGET TO IMPLEMENT API RATE LIMITS
// 5 requests per second
// https://ncaa-api.henrygd.me/openapi
// THE APPLICATION WILL LOOK WEIRD IF YOU GET RATE LIMITED
data class Game(
    val id: String,
    val home: String,
    val away: String,
    val homeScore: Int?,
    val awayScore: Int?,
    val homeWon: Boolean,
    val awayWon: Boolean,
    val gameState: String,
    val startDate: String,
    val currentPeriod: String,
    val contestClock: String,
    val gender: String,
)
