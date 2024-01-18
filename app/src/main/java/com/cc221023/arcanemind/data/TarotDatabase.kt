package com.cc221023.arcanemind.data


import androidx.room.Database
import androidx.room.RoomDatabase
import com.cc221023.arcanemind.TarotCard
@Database(entities = [TarotCard::class], version = 1)
abstract class TarotDatabase : RoomDatabase() {
    abstract fun tarotDao(): TarotDao
}