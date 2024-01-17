package com.cc221023.arcanemind.utils;

import com.cc221023.arcanemind.TarotCard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



    public class JsonUtils {
        public static com.cc221023.arcanemind.utils.JsonUtils JsonUtils;

        // Parses a JSONObject of Cards into a List
    /* SCHEMA
        cards[]             []
            name            str
            name_short      str
            value           str
            value_int       int
            suit            str
            arcana          str
            meaning_up      str
            meaning_rev     str
            desc            str
     */
        public static List<TarotCard> parseCardsFromJson(String jsonString) throws JSONException, IOException {
            JSONObject cardsJSON = new JSONObject(jsonString);
            int hits = cardsJSON.getInt("nhits");
            JSONArray jsonArray = cardsJSON.getJSONArray("cards");
            List<TarotCard> parsedCards = new ArrayList<>(hits);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonCard = jsonArray.getJSONObject(i);
                parsedCards.add(pluckJsonCard(jsonCard));
            }

            return parsedCards;
        }

        // Converts JSONObject -> Card
        private static TarotCard pluckJsonCard(JSONObject TarotCard) throws JSONException {
            return new TarotCard(
                    TarotCard.getString("name"),
                    TarotCard.getString("name_short"),
                    TarotCard.getString("value"),
                    TarotCard.getInt("value_int"),
                    TarotCard.getString("suit"),
                    TarotCard.getString("arcana"),
                    TarotCard.getString("meaning_up"),
                    TarotCard.getString("meaning_rev"),
                    TarotCard.getString("desc"),

                    false);
        }

        private static String toTitleCase(String s) {
            if (s == null || s.length() <= 0) return s;
            StringBuilder sb = new StringBuilder();
            sb.append(Character.toUpperCase(s.charAt(0)));
            for (int i = 1; i < s.length(); i++) {
                sb.append(Character.toLowerCase(s.charAt(i)));
            }
            return sb.toString();
        }
    }
