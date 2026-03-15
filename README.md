[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/NYuLn2p4)

# Android App 2 - Basketball Scores
Name: Saaswath Kumar

Computing ID: anr6nr

## Code Structure

model:
* Contains all the Room, Dao, and data class stuff
* Files: 
Game.kt, GameDao.kt, GameDatabase.kt, GameRepo.kt, GameResponseDetails.kt, NcaaApiService.kt

view:
* Contains GameActivity.kt, the main view that's launched from MainActivity, containing the activity for viewing basketball scores

viewmodel
* Contains GameViewModel, 

## Citations

GameDao
 * REFERENCES
 * Title: Accessing data using Room DAOs
 * Author: Android Developers
 * URL: https://developer.android.com/training/data-storage/room/accessing-data?utm_source=android-studio-app&utm_medium=app
 * Software License: Apache 2 License
 * Usage: I used this to find out how to set up Dao and how to use it to do SQL queries

GameDatabase
 * REFERENCES
 * Title: Persist data with Room
 * Author: Android Developers
 * URL:https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room?authuser=2#0
 * Date: May 17, 2024
 * Software License: Apache 2 License
 * Usage: I used this to learn about Room and used it as a reference for my implementation

GameRepo
 * REFERENCES
 * Title:  Read network state
 * Author: Android Developers
 * URL:https://developer.android.com/develop/connectivity/network-ops/reading-network-state
 * Software License: Apache 2 License
 * Usage: I used this to learn and get reference for the networks stuff for the app

NcaaApiService
 * REFERENCES
 * Title:  Retrofit Documentation
 * Author: Square Inc
 * URL:https://square.github.io/retrofit/
 * Software License: Apache 2.0 License
 * Usage: I used this to learn how to use Retrofit for my project

GameActivity
 * REFERENCES
 * Title:  Pull to refresh
 * Author: Android Developers
 * URL:https://developer.android.com/develop/ui/compose/components/pull-to-refresh
 * Software License: Apache 2 License
 * Usage: I used this to component for the pull to refresh for my app
 * ---
 * REFERENCE
 * Title: Date pickers
 * Author: Android Developers
 * URL: https://developer.android.com/develop/ui/compose/components/datepickers
 * Software License: Apache 2 License
 * Usage: I used the example code for reference to do the calendar
 * ---
 * REFERENCE
 * Title: Side-effects in Compose
 * Author: Android Developers
 * URL: https://developer.android.com/develop/ui/compose/side-effects#disposableeffect
 * Software License: Apache 2 License
 * Usage: I used this code to make an event so that connecting to wifi from offline refreshes the app


