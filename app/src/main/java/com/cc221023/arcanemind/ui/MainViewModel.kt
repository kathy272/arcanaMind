package com.cc221023.arcanemind.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController

import com.cc221023.arcanemind.TarotCard
import com.cc221023.arcanemind.TarotCardRepository
import com.cc221023.arcanemind.Utils
import com.cc221023.arcanemind.data.TarotDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONArray


class MainViewModel(private val dao: TarotDao, private val context: Context) : ViewModel() {
    private val _tarotCardState = MutableStateFlow(TarotCard("", "", "", 0, "", "", ""))
    val tarotCardState: StateFlow<TarotCard> = _tarotCardState.asStateFlow()
    private val tarotCardRepository = TarotCardRepository(context)

    private val _mainViewState = MutableStateFlow(MainViewState())
    val mainViewState: StateFlow<MainViewState> = _mainViewState.asStateFlow()

    val tarotCard = dao.getTarotCard(1)
    private var tarotCards: List<TarotCard> = emptyList()

    fun loadTarotCards() {
        tarotCards = tarotCardRepository.getTarotCardsFromJson()
    }

    fun fetchRandomTarotCard() {
        if (tarotCards.isNotEmpty()) {
            val randomIndex = tarotCards.indices.random()
            val randomCard = tarotCards[randomIndex]
            viewModelScope.launch {
                _tarotCardState.value = randomCard
                Log.d("APItest", "Selected Card: $randomCard")

            }
        }
    }
    fun parseJsonArray(jsonArray: JSONArray): List<TarotCard> {
        val tarotCards = mutableListOf<TarotCard>()

        for (i in 0 until jsonArray.length()) {
            val jsonCard = jsonArray.getJSONObject(i)
            val tarotCard = Utils.pluckJsonCard(jsonCard)
            tarotCard?.let {
                tarotCards.add(it)
            }
        }

        return tarotCards
    }


    // Navigation

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



}


