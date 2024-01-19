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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MainViewModel(private val dao: TarotDao, private val context: Context) : ViewModel() {
    private val _tarotCardState = MutableStateFlow(TarotCard("","", "", "", 0, "", "", ""))
    val tarotCardState: StateFlow<TarotCard> = _tarotCardState.asStateFlow()
    private val tarotCardRepository = TarotCardRepository(context)

    private val _mainViewState = MutableStateFlow(MainViewState())
    val mainViewState: StateFlow<MainViewState> = _mainViewState.asStateFlow()
    private var tarotCards: List<TarotCard> = emptyList()

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

    fun saveRandomCard(tarotCard: RandomDaily) {
        viewModelScope.launch {
            dao.insert(tarotCard)
            Log.d("APItest", "Saved Card: $tarotCard")
        }
    }

    //get major arcana cards
    private val _majorArcanaCards = MutableStateFlow<List<TarotCard>>(emptyList())
    val majorArcanaCards: StateFlow<List<TarotCard>> get() = _majorArcanaCards

    init {
        loadMajorArcanaCards()
    }

    private fun loadMajorArcanaCards() {
        viewModelScope.launch {
            _majorArcanaCards.value = tarotCardRepository.getMajorArcanaCards()
        }
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


