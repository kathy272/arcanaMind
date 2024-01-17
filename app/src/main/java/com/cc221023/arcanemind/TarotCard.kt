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
data class TarotCard(
    @ColumnInfo(name = "name_short") val nameShort: String,
    val name: String,
    val value: String,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "value_int") val id: Int, // Change the type to Int
    @ColumnInfo(name = "meaning_up") val meaningUp: String,
    @ColumnInfo(name = "meaning_rev") val meaningRev: String,
    val desc: String
)




//    override fun toString(): String {
//        return name
//    }

//@Entity(tableName = "tarot_cards")
//data class TarotCard(
//    val type: String,
//    val name: String,
//    val value: Int,
//    val meaning: String,
//    val description: String,
//    @PrimaryKey(autoGenerate = true) val id: Int = 0
//)


// Updated readJsonFile function
//fun readJsonFile(): List<TarotCard> {
//    val jsonFileName = "card_data.json"
//    val json: String?
//
//    try {
//        // Use the context from the viewModelScope
//        val inputStream: InputStream = ApplicationProvider.getApplicationContext<Application>().assets.open(jsonFileName)
//        json = inputStream.bufferedReader().use { it.readText() }
//    } catch (ex: IOException) {
//        ex.printStackTrace()
//        return emptyList()
//    }
//
//    // Use Gson to parse the JSON into a list of TarotCard objects
//    val gson = Gson()
//    val typeToken = object : TypeToken<List<TarotCard>>() {}.type
//    return gson.fromJson(json, typeToken)
//}



