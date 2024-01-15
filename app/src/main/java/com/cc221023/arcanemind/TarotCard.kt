package com.cc221023.arcanemind

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity (tableName = "tarot_cards")
data class TarotCard(
    val image: String,
    val name: String,
    val date: String,
    val description: String,
    val comment: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)