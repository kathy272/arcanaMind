package com.cc221023.arcanemind


//@Entity (tableName = "tarot_cards")
//data class TarotCard(
//    val image: String,
//    val name: String,
//    val date: String,
//    val description: String,
//    val comment: String,
//    @PrimaryKey(autoGenerate = true) val id: Int = 0
//)

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tarot_cards")
class TarotCard    // Constructor allows room to re-create Card objects from database
    (// swkn
    @field:ColumnInfo(name = "name_short") val nameShort: String, // Knight of Swords
    val name: String, // Knight
    val value: String, // 12
    @field:ColumnInfo(name = "value_int") val valueInt: Int, // Swords
    val suit: String, // minor (ekelen name: type)
    val arcana: String, // Skill, bravery...
    @field:ColumnInfo(name = "meaning_up") val meaningUp: String, // Imprudence, incapacity...
    @field:ColumnInfo(name = "meaning_rev") val meaningRev: String, // He is riding in full course, as if scattering his enemies...
    val desc: String, var hasImage: Boolean
) {

    @PrimaryKey(autoGenerate = true)
    var id = 0

    override fun toString(): String {
        return name
    }
}