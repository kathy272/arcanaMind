package com.cc221023.arcanemind

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_cards")
data class RandomDaily (
    val name: String = "",
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val meaningUp: String = "",
    val desc: String = "",
    val comment: String = "",
)