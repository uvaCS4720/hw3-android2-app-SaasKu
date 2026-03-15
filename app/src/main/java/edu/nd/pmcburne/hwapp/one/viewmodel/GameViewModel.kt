package edu.nd.pmcburne.hwapp.one.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.RequiresPermission
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import edu.nd.pmcburne.hwapp.one.model.Game
import edu.nd.pmcburne.hwapp.one.model.GameDao
import edu.nd.pmcburne.hwapp.one.model.GameDatabase
import edu.nd.pmcburne.hwapp.one.model.GameRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate


class GameViewModel(application: Application): AndroidViewModel(application) {
    private val dao: GameDao = GameDatabase.getDatabase(application).gameDao()
    private val repo: GameRepo = GameRepo(application, dao)

    private val gameList: MutableStateFlow<List<Game>> = MutableStateFlow(emptyList())
    val gl: StateFlow<List<Game>> = gameList.asStateFlow()

    private val loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val ld: StateFlow<Boolean> = loading.asStateFlow()

    private val gender: MutableStateFlow<String> = MutableStateFlow("men")
    val gn: StateFlow<String> = gender.asStateFlow()

    private val dateSelected: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())
    val ds: StateFlow<LocalDate> = dateSelected.asStateFlow()

    private val _initialized = MutableStateFlow(false)
    val initialized: StateFlow<Boolean> = _initialized.asStateFlow()

    private val isOnline = MutableStateFlow(false)
    val isOn: StateFlow<Boolean> = isOnline.asStateFlow()

    init{
        loadGames()
    }


    fun loadGames(){
        _initialized.value = true
        viewModelScope.launch{
            loading.value = true
            gameList.value = repo.getGames(
                gender = gender.value,
                year= dateSelected.value.year.toString(),
                month = dateSelected.value.monthValue.toString().padStart(2, '0'),
                day = dateSelected.value.dayOfMonth.toString().padStart(2, '0')
            )
            loading.value = false
        }
    }

    fun setGender(newGender: String){
        gender.value = newGender
        loadGames()
    }

    fun setDate(date: LocalDate){
        dateSelected.value = date
        loadGames()
    }


    fun setOnline(online: Boolean){
        val wasOffline = !isOnline.value
        isOnline.value = online
        if (wasOffline && online) {
            loadGames()
        }
    }

}