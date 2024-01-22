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

class MainViewModel(private val dao: TarotDao, context: Context) : ViewModel() {

    private val _tarotCardState = MutableStateFlow(TarotCard("","", "", "",  0, "", "", ""))
    val tarotCardState: StateFlow<TarotCard> = _tarotCardState.asStateFlow()
    private val tarotCardRepository = TarotCardRepository(context)

    private val _mainViewState = MutableStateFlow(MainViewState())
    val mainViewState: StateFlow<MainViewState> = _mainViewState.asStateFlow()
    private var tarotCards: List<TarotCard> = emptyList()


    private val _randomDailyState = MutableStateFlow(RandomDaily("",0,"","","","","",""))
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
    fun fetchCardDetails(nameShort: String) { //to fetch card details by nameShort
        Log.d("APItest", "Tries fetching $nameShort")
        val card = tarotCards.find { it.nameShort == nameShort }

        if (card != null) {
            viewModelScope.launch {
                _tarotCardState.value = card
                Log.d("APItest", "Selected Card: $card")
            }
        } else {
            Log.e("APItest", "Card with nameShort $nameShort not found")
        }
    }


    fun saveRandomCard(dailyCard: RandomDaily) {
        Log.d("APItest", "Tries saving")
        viewModelScope.launch {
            Log.d("APItest","launched before insert, dailyCard: ${dailyCard}")
            withContext(Dispatchers.IO) {
                dao.insert(dailyCard)
                Log.d("APItest","launched after insert, dailyCard: ${dailyCard}")

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
                Log.d("Delete","got all cards")
            }
        }
    }

    fun deleteButton(dailyCard: RandomDaily){
        viewModelScope.launch {
            dao.delete(dailyCard)
            Log.d("Delete","Clicked on delete, dailyCard: ${dailyCard}")
        }
        getAllDailyCards()
        Log.d("Delete","get all cards")
    }

    fun updateRandomDailyCard(dailyCard: RandomDaily){
        viewModelScope.launch {
            dao.update(dailyCard)
        }
        getAllDailyCards()
        closeDialog()
    }

    fun editRandomDailyCard(dailyCard: RandomDaily){
        _randomDailyState.value = dailyCard
        _mainViewState.update { it.copy(openDialog = true) }
    }

    fun closeDialog(){
        _mainViewState.update { it.copy(openDialog = false) }
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
    private val _minorArcanaCards = MutableStateFlow<List<TarotCard>>(emptyList())
    val minorArcanaCards: StateFlow<List<TarotCard>> get() = _minorArcanaCards
    init {
        loadMinorArcanaCards()
    }
    private fun loadMinorArcanaCards() {
        viewModelScope.launch {
            _minorArcanaCards.value = tarotCardRepository.getMinorArcanaCards()
        }
    }



    //Navigation
    fun selectScreen(screen: Screens){
        _mainViewState.update { it.copy(selectedScreen = screen) }
    }

}