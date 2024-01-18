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
    suspend fun insert(tarot_card: RandomDaily?)

    @Update
    fun update(tarot_card: RandomDaily?)

    @Delete
    fun delete(tarot_card: RandomDaily?)

    @Query("DELETE FROM tarot_cards")
    fun deleteAllCards()

    @Query("SELECT * FROM tarot_cards ORDER BY id")
    fun getAllTarotCards(): LiveData<List<TarotCard>>

    @Query("SELECT * FROM tarot_cards WHERE id = :id")
    fun getTarotCard(id: Int): LiveData<TarotCard>

    @Query("SELECT * FROM daily_cards ORDER BY id")
    fun getAllRandomCards(): LiveData<List<RandomDaily>>

    @Query("SELECT * FROM daily_cards WHERE id = :id")
    fun getDailyCard(id: Int): LiveData<RandomDaily>}
//@Dao
//interface TarotDao {
//    @Insert
//    fun insert(tarot_card: TarotCard?)
//
//    @Update
//    fun update(tarot_card: TarotCard?)
//
//    @Delete
//    fun delete(tarot_card: TarotCard?)
//
//    @Query("DELETE FROM tarot_cards")
//    fun deleteAllCards()
//
//    @Query("SELECT * FROM tarot_cards ORDER BY id")
//    fun getAllTarotCards(): LiveData<List<TarotCard>>
//
//    @Query("SELECT * FROM tarot_cards WHERE id = :id")
//    fun getTarotCard(id: Int): LiveData<TarotCard>}
