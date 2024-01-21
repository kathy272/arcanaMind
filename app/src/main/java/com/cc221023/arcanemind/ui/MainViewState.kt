package com.cc221023.arcanemind.ui

import com.cc221023.arcanemind.RandomDaily
import com.cc221023.arcanemind.TarotCard

data class MainViewState (
    val selectedScreen: Screens = Screens.Home,
    val tarotCard: TarotCard = TarotCard( "","","","",0,"","",""),
    val randomDaily: RandomDaily = RandomDaily("",0,"","","","","",""),
    val daily_cards: List<RandomDaily> = emptyList(),
    val AllTarotCardsList: List<TarotCard> = emptyList(),
    val openDialog: Boolean = false
)