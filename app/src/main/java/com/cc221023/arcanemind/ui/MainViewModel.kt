package com.cc221023.arcanemind.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.cc221023.arcanemind.RandomDaily

import com.cc221023.arcanemind.TarotCard
import com.cc221023.arcanemind.TarotCardRepository
import com.cc221023.arcanemind.data.TarotDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel(private val dao: TarotDao, private val context: Context) : ViewModel() {
    private val _tarotCardState = MutableStateFlow(TarotCard("","", "", "",  0, "", "", ""))
    val tarotCardState: StateFlow<TarotCard> = _tarotCardState.asStateFlow()
    private val tarotCardRepository = TarotCardRepository(context)

    private val _mainViewState = MutableStateFlow(MainViewState())
    val mainViewState: StateFlow<MainViewState> = _mainViewState.asStateFlow()
    private var tarotCards: List<TarotCard> = emptyList()


    private val _randomDailyState = MutableStateFlow(RandomDaily("",0,"","","","","",0))
    val randomDailyState: StateFlow<RandomDaily> = _randomDailyState.asStateFlow()
    init { //to initalize the tarot cards when the app is opened
        tarotCards = tarotCardRepository.getTarotCardsFromJson()
    }
    fun fetchRandomTarotCard() { //to fetch a random card
        if (tarotCards.isNotEmpty()) {
            val randomIndex = tarotCards.indices.random()
            val randomCard = tarotCards[randomIndex]
            viewModelScope.launch {
                _tarotCardState.value = randomCard
                Log.d("APItest", "Selected Card: $randomCard")

            }
        }
    }

    fun saveRandomCard(dailyCard: RandomDaily) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dao.insert(dailyCard)
            }
            Log.d("APItest", "Saved Card: $dailyCard")

//check if its actually saved
dao.getAllDailyCards().collect() { allRandomCards ->
                Log.d("APItest", "All Random Cards: $allRandomCards")
            }
        }
    }

    fun getAllDailyCards( ) {
        viewModelScope.launch {
            dao.getAllDailyCards().collect() { allRandomCards ->
                _mainViewState.update { it.copy(daily_cards = allRandomCards) }
                Log.d("AllCardsRandom", "All Cards: $allRandomCards")
            }
        }
    }
fun updateDailyRandomCard(dailyCard: RandomDaily){
    viewModelScope.launch {
        dao.update(dailyCard)
    }
getAllDailyCards()
}
    //Navigation
    fun selectScreen(screen: Screens){
        _mainViewState.update { it.copy(selectedScreen = screen) }
    }
    // Account and Info missing
    fun navigateToHomeScreen(navController: NavController) {
        navController.navigate(Screens.Home.route)
    }
    fun navigateToDrawDailyScreen(navController: NavController) {
        navController.navigate(Screens.DrawDaily.route)
    }

    fun navigateToMinorArcanaScreen(navController: NavController){
        navController.navigate((Screens.MinorArcana.route))
    }
    fun navigateToMajorArcanaScreen(navController: NavController){
        navController.navigate((Screens.MajorArcana.route))
    }


}


