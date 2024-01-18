package com.cc221023.arcanemind.ui

import com.cc221023.arcanemind.RandomDaily
import com.cc221023.arcanemind.TarotCard

data class MainViewState (
    val selectedScreen: Screens = Screens.Home,
val tarotCard: TarotCard = TarotCard( "","","",0,"","",""),
val randomDaily: RandomDaily = RandomDaily("",0,"","","")

)