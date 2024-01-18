package com.cc221023.arcanemind

import android.content.Context
import com.cc221023.arcanemind.utils.Utils
import org.json.JSONArray
import org.json.JSONObject
//from the json file
class TarotCardRepository(private val context: Context) {

    //reads the json file and returns a list of TarotCard objects
    fun getTarotCardsFromJson(): List<TarotCard> {
        val jsonString = Utils.getJsonDataFromAsset(context, "card_data.json")
        return parseJsonArray(JSONArray(jsonString)) //converts the json string to a json array
    }


    //parses the json array and returns a list of TarotCard objects
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
    //takes a single json object and returns a single TarotCard object
    private fun pluckJsonCard(jsonObject: JSONObject): TarotCard? {
        return TarotCard(
            jsonObject.getString("type"),
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
