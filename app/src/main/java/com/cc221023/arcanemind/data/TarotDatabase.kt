package com.cc221023.arcanemind.data


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cc221023.arcanemind.RandomDaily
import com.cc221023.arcanemind.TarotCard
@Database(entities = [TarotCard::class, RandomDaily::class], version = 2)
abstract class TarotDatabase : RoomDatabase() {
    abstract fun tarotDao(): TarotDao


}