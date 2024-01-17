package com.cc221023.arcanemind.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.cc221023.arcanemind.CardRepository
import com.cc221023.arcanemind.TarotCard
import com.cc221023.arcanemind.data.TarotDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel(private val dao: TarotDao): ViewModel() {
    //private val _tarotCardState = MutableStateFlow(TarotCard("", "", "","", "" ))
   // val tarotCardState: StateFlow<TarotCard> = _tarotCardState.asStateFlow()

    private val _mainViewState = MutableStateFlow(MainViewState())
    val mainViewState: StateFlow<MainViewState> = _mainViewState.asStateFlow()

   val tarotCard = dao.getTarotCard( 1 )
     //val _tarotCardState.update { it.copy(tarotCard = tarotCard)}

//    fun fetchTarotCard() {
//        viewModelScope.launch {
//            try {
//               val result= CardRepository.getTarotCard()
//                val tarotCard = result.cards[0]
//                withContext(Dispatchers.IO) {
//                    dao.insert(tarotCard)
//                }
//                _tarotCardState.update { it.copy(tarotCard = tarotCard)}
//            } catch (e: Exception) {
//                Log.e("MainViewModel", "Error fetching tarot card", e)
//            }
//        }
//    }


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


