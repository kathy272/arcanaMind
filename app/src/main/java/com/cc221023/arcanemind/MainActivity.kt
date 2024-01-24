package com.cc221023.arcanemind

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

import com.cc221023.arcanemind.data.TarotDatabase
import com.cc221023.arcanemind.ui.MainView
import com.cc221023.arcanemind.ui.MainViewModel
import com.cc221023.arcanemind.ui.theme.ArcaneMindTheme



class MainActivity : ComponentActivity() {
    private val db by lazy {
      Room.databaseBuilder(this, TarotDatabase::class.java, "TarotDatabase.db")
          .addMigrations( object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Perform the necessary database schema changes
            // For example, adding a new column or modifying an existing one
            database.execSQL("ALTER TABLE tarot_cards ADD COLUMN new_column TEXT")
        }
    })
          .build()
    }
    private val mainViewModel by viewModels<MainViewModel>(
        factoryProducer ={
        object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(db.tarotDao(),application ) as T
            }
        }
    }
            )
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArcaneMindTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {

                    MainView(mainViewModel)
                }
            }
        }
    }
}

