package com.cc221023.arcanemind.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.cc221023.arcanemind.TarotCard
import com.cc221023.arcanemind.data.TarotDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class MainViewModel(private val dao: TarotDao): ViewModel() {
    private val _tarotCardState = MutableStateFlow(TarotCard("", "", "","", "" ))
    val tarotCardState: StateFlow<TarotCard> = _tarotCardState.asStateFlow()

    private val _mainViewState = MutableStateFlow(MainViewState())
    val mainViewState: StateFlow<MainViewState> = _mainViewState.asStateFlow()


    //Navigation
    fun selectScreen(screen: Screens){
        _mainViewState.update { it.copy(selectedScreen = screen) }
    }
    fun navigateToHomeScreen(navController: NavController) {
        navController.navigate(Screens.Home.route)
    }
    fun navigateToDrawDailyScreen(navController: NavController) {
        navController.navigate(Screens.DrawDaily.route)
    }
}

