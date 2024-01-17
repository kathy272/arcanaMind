package com.cc221023.arcanemind.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cc221023.arcanemind.TarotCard
import kotlinx.coroutines.flow.Flow

//@Dao
//interface TarotDao {
//    @Insert
//    suspend fun insert(tarot_card: TarotCard)
//
//    @Update
//    suspend fun update(tarot_card: TarotCard): Any?
//
//    @Delete
//    suspend fun delete(tarot_card: TarotCard): Any?
//
//    @Query("SELECT * FROM tarot_cards ORDER BY id DESC")
//    fun getAllTarotCards(): Flow<List<TarotCard>>
//
//    @Query("SELECT * FROM tarot_cards WHERE id = :id")
//    fun getTarotCard(id: Int): LiveData<TarotCard>
//
//
//}

@Dao
interface TarotDao {
    @Insert
    fun insert(tarot_card: TarotCard?)

    @Update
    fun update(tarot_card: TarotCard?)

    @Delete
    fun delete(tarot_card: TarotCard?)

    @Query("DELETE FROM tarot_cards")
    fun deleteAllCards()

    @get:Query("SELECT * FROM tarot_cards ORDER BY id")
    val allTarotCards: LiveData<List<TarotCard>>

    @get:Query("SELECT * FROM tarot_cards WHERE id = id")
  val getTarotCard: LiveData<TarotCard>

  @Query("SELECT * FROM tarot_cards WHERE id = :id")
    fun getTarotCard(id: Int): LiveData<TarotCard>
}