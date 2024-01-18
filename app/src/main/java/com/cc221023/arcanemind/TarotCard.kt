package com.cc221023.arcanemind

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//for the database
@Entity(tableName = "tarot_cards")
data class TarotCard(
    val type: String,
    val nameShort: String,
    val name: String,
    val value: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int, // Change the type to Int
    val meaningUp: String,
    val meaningRev: String,
    val desc: String
)




