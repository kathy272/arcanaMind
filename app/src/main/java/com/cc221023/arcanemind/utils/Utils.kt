package com.cc221023.arcanemind.utils

import android.content.Context
import java.io.IOException
class Utils {
    companion object {

// get json data from assets folder
        fun getJsonDataFromAsset(context: Context, fileName: String): String? {
            return try {

                context.assets.open(fileName).bufferedReader().use { it.readText()
                }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                null
            }
        }


    }

}
