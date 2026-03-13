package edu.nd.pmcburne.hwapp.one.model

// Example to base it on:
// https://ncaa-api.henrygd.me/scoreboard/basketball-men/d1/2026/02/17

data class GameResponseDetails(
    val games: List<GameWrapper>
)

data class GameWrapper(
    val game: GameInfo
)

data class GameInfo(
    val gameID: String,
    val gameState: String,
    val startDate: String,
    val startTime: String,
    val currentPeriod: String,
    val contestClock: String,
    val home: TeamInfo,
    val away: TeamInfo,
)

data class TeamInfo(
    val score: String?,
    val winner: Boolean,
    val names: TeamNames,
)

data class TeamNames(
    val shortName: String,
)


