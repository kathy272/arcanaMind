package com.cc221023.arcanemind.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cc221023.arcanemind.RandomDaily
import com.cc221023.arcanemind.TarotCard
import kotlinx.coroutines.flow.Flow


@Dao
interface TarotDao {
    @Insert
    suspend fun insert(dailyCard: RandomDaily)

    @Update
    suspend fun update(dailyCard: RandomDaily)

    @Delete
    suspend fun delete(dailyCard: RandomDaily)

    @Query("DELETE FROM tarot_cards")
    fun deleteAllCards()

    //get all tarot cards from the database (API)
    @Query("SELECT * FROM tarot_cards ORDER BY id")
    fun getAllTarotCards(): LiveData<List<TarotCard>>

    @Query("SELECT * FROM tarot_cards WHERE id = :id")
    fun getTarotCard(id: Int): LiveData<TarotCard>

    //get all daily cards from the database (saved cards)
    @Query("SELECT * FROM daily_cards")
    fun getAllDailyCards(): Flow<List<RandomDaily>>

    @Query("SELECT * FROM daily_cards WHERE id = :id")
    fun getDailyCard(id: Int): LiveData<RandomDaily>




}
