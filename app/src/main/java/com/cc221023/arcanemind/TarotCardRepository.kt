package com.cc221023.arcanemind

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

class TarotCardRepository(private val context: Context) {

    fun getTarotCardsFromJson(): List<TarotCard> {
        val jsonString = Utils.getJsonDataFromAsset(context, "card_data.json")
      //  Log.d("TarotCardRepository", "JSON String: $jsonString")

        return parseJsonArray(JSONArray(jsonString))
    }


    private fun parseJsonArray(jsonArray: JSONArray): List<TarotCard> {
        val tarotCards = mutableListOf<TarotCard>()

        for (i in 0 until jsonArray.length()) {
            val jsonCard = jsonArray.getJSONObject(i)
            val tarotCard = pluckJsonCard(jsonCard)
            tarotCard?.let {
                tarotCards.add(it)
            }
        }

        return tarotCards
    }
    private fun pluckJsonCard(jsonObject: JSONObject): TarotCard? {
        return TarotCard(
            jsonObject.getString("name_short"),
            jsonObject.getString("name"),
            jsonObject.getString("value"),
            jsonObject.getInt("value_int"),
            jsonObject.getString("meaning_up"),
            jsonObject.getString("meaning_rev"),
            jsonObject.getString("desc"),
        )
    }
}
