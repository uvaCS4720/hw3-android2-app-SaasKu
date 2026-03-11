package edu.nd.pmcburne.hwapp.one.model

import org.json.JSONObject

fun parse(json: String, gender: String): List<Game>{
    val root = JSONObject(json)
    val gArray = root.getJSONArray("games")
    val games = mutableListOf<Game>()

    for (i in 0 until gArray.length()){
        val gObj = gArray.getJSONObject(i).getJSONObject("game")
        val homeObj = gObj.getJSONObject("home")
        val awayObj = gObj.getJSONObject("away")
        val homeNames = homeObj.getJSONObject("names")
        val awayNames = awayObj.getJSONObject("names")

        val game = Game(
            id = gObj.getString("gameID"),

//          Since the full name is usually sometimes blank I'm just going with the short name
            home=homeNames.getString("short"),
            away=awayNames.getString("short"),
            homeScore=homeObj.getString("score").toIntOrNull(),
            awayScore=awayObj.getString("score").toIntOrNull(),
            homeWon=homeObj.getBoolean("winner"),
            awayWon=awayObj.getBoolean("winner"),
            gameState=gObj.getString("gameState"),
            startDate=gObj.getString("startDate"),
            currentPeriod=gObj.getString("currentPeriod"),
            contestClock=gObj.getString("contestClock"),
            gender=gender,
        )
        games.add(game)
    }
    return games
}
