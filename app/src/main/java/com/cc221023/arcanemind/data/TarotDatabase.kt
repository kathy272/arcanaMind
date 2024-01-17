package com.cc221023.arcanemind.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cc221023.arcanemind.TarotCard


//@Database(entities = [TarotCard::class], version = 1, exportSchema = false)
//abstract class TarotDatabase : RoomDatabase() {
//    abstract val dao: TarotDao
//
//
//}

//
//@Database(entities = [TarotCard::class], version = 1)
//public abstract class TarotDatabase : RoomDatabase() {
//    abstract fun tarotDao(): TarotDao
//    private abstract class PopulateDbAsyncTask(db: TarotDatabase?) :
//        AsyncTask<Void?, Void?, Void?>() {
//        private val tarotDao: TarotDao
//
//        init {
//            tarotDao = db!!.tarotDao()
//        }
//@Suppress("DEPRECATION")
//        protected override fun doInBackground(vararg params: Void?): Void? {
//            //todo: Instantiate date for the first time
//            return null
//        }
//    }
//
//    companion object {
//        private var instance: TarotDatabase? = null
//        @Synchronized
//        fun getInstance(context: Context): TarotDatabase? {
//            if (instance == null) {
//                instance = databaseBuilder(
//                    context.applicationContext,
//                    TarotDatabase::class.java, "TarotDatabase"
//                )
//                    .fallbackToDestructiveMigration()
//                    .addCallback(roomCallback)
//                    .build()
//            }
//            return instance
//        }
//
//        private val roomCallback: Callback = object : Callback() {
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                super.onCreate(db)
//                PopulateDbAsyncTask(instance).execute()
//            }
//        }
//    }
//}

@Database(entities = [TarotCard::class], version = 3)
abstract class TarotDatabase : RoomDatabase() {
    abstract fun tarotDao(): TarotDao
    private class PopulateDbAsyncTask(db: TarotDatabase?) :
        AsyncTask<Void?, Void?, Void?>() {
        private val tarotDao: TarotDao

        init {
            tarotDao = db!!.tarotDao()
        }

        protected override fun doInBackground(vararg params: Void?): Void? {
            //todo: Instantiate date for the first time
            return null
        }
    }

    companion object {
        private var instance: TarotDatabase? = null
        @JvmStatic
        fun getInstance(context: Context): TarotDatabase? {
            if (instance == null) {
                instance = databaseBuilder(
                    context.applicationContext,
                    TarotDatabase::class.java, "tarot_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance
        }



        private val roomCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance).execute()
            }
        }
    }
}