package edu.nd.pmcburne.hwapp.one
import edu.nd.pmcburne.hwapp.one.model.parseData
import junit.framework.TestCase.assertEquals


// Data link for this test:
// https://ncaa-api.henrygd.me/scoreboard/basketball-men/d1/2025/03/02
import org.junit.Test

class parseTest {
    @Test
    fun testParseData(){
        val json = """
        {
  "inputMD5Sum": "3813ff4578dd5bd5348e0302bcf1bd2d",
  "instanceId": "379fb71fe5b9445486b5421ff576282c",
  "updated_at": "03-17-2025 14:01:28",
  "games": [
    {
      "game": {
        "gameID": "2537402",
        "away": {
          "score": "70",
          "names": {
            "char6": "UC DAV",
            "short": "UC Davis",
            "seo": "uc-davis",
            "full": "University of California, Davis"
          },
          "winner": false,
          "seed": "",
          "description": "(15-14)",
          "rank": "",
          "conferences": [
            {
              "conferenceName": "Big West",
              "conferenceSeo": "big-west"
            }
          ]
        },
        "finalMessage": "FINAL",
        "bracketRound": "",
        "title": "Hawaii UC Davis",
        "contestName": "",
        "url": "/game/6351149",
        "network": "",
        "home": {
          "score": "78",
          "names": {
            "char6": "HAWAII",
            "short": "Hawaii",
            "seo": "hawaii",
            "full": "University of Hawaii, Manoa"
          },
          "winner": true,
          "seed": "",
          "description": "(15-14)",
          "rank": "",
          "conferences": [
            {
              "conferenceName": "Big West",
              "conferenceSeo": "big-west"
            }
          ]
        },
        "liveVideoEnabled": false,
        "startTime": "12:00AM ET",
        "startTimeEpoch": "1740891600",
        "bracketId": "",
        "gameState": "final",
        "startDate": "03-02-2025",
        "currentPeriod": "FINAL",
        "videoState": "",
        "bracketRegion": "",
        "contestClock": "0:00"
      }
    }
  ],
  "hideRank": false
}
    """.trimIndent()

        val games = parseData(json, gender="men")
        assertEquals(1, games.size)
        assertEquals("2537402", games[0].id)
        assertEquals("Hawaii", games[0].home)
        assertEquals("UC Davis", games[0].away)
        assertEquals(78, games[0].homeScore)
        assertEquals(70, games[0].awayScore)
        assertEquals("final", games[0].gameState)
        assertEquals("03-02-2025", games[0].startDate)
        assertEquals("men", games[0].gender)
    }



}