package com.cc221023.arcanemind

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
class Utils {
    companion object {

@Throws(JSONException::class)
 fun pluckJsonCard(TarotCard: JSONObject): TarotCard? {
    return TarotCard(
        TarotCard.getString("name_short"),
        TarotCard.getString("name"),
        TarotCard.getString("value"),
        TarotCard.getInt("value_int"),
        TarotCard.getString("meaning_up"),
        TarotCard.getString("meaning_rev"),
        TarotCard.getString("desc"),  //boolean image
    )
}
        fun getJsonDataFromAsset(context: Context, fileName: String): String? {
            return try {

                context.assets.open(fileName).bufferedReader().use { it.readText()
                }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                null
            }
        }

        fun tarotCardList(context: Context): MutableList<TarotCard> {
            val jsonFileString = getJsonDataFromAsset(context, "card_data.json")

            return if (jsonFileString != null) {
                val listCardType = object : TypeToken<List<TarotCard>>() {}.type
                Gson().fromJson(jsonFileString, listCardType)
            } else {
                mutableListOf()
            }
        }
    }

}
