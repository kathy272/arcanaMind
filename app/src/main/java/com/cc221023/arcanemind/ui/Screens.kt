package com.cc221023.arcanemind.ui

sealed class Screens (val route:String){
    object Home: Screens("home")
    object Info: Screens("info")
    object Account: Screens("account")
}
