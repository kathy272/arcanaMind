package com.cc221023.arcanemind.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cc221023.arcanemind.TarotCard
import kotlinx.coroutines.flow.Flow

interface TarotDao {
    @Insert
    suspend fun insertTarotCard(tarot_card: TarotCard)

    @Update
    suspend fun updateTarotCard(tarot_card: TarotCard)

    @Delete
    suspend fun deleteTarotCard(tarot_card: TarotCard)

    @Query("SELECT * FROM tarot_cards")
    fun getAllTarotCards(): Flow<List<TarotCard>>
}