package com.cc221023.arcanemind

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


//Cardviewmodel
//// Networking package or file
//
//import retrofit2.Response
//import retrofit2.http.GET
//
//interface TarotCardApiService {
//    @GET("endpoint")
//    suspend fun getTarotCard(): Response<TarotCardResponse>
//}
//
//data class TarotCardResponse(
//    val cards: List<TarotCard>
//)
//// Networking package or file

class CardViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CardRepository
    private val cardList: LiveData<List<TarotCard>?>
    private val iSelected: MutableLiveData<Int>
    private val cardImages: MutableLiveData<HashMap<String, Drawable>>? = null

    init {
        repository = CardRepository(application)
        cardList =  repository.getTarotCardList()
        iSelected = MutableLiveData()
        if (cardList.value == null || cardList.value!!.isEmpty()) {
            fetchRandomTarot(3)
        }
    }

    fun insert(c: TarotCard?) {
        repository.insert(c)
    }

    fun update(c: TarotCard?) {
        repository.update(c)
    }

    fun delete(c: TarotCard?) {
        repository.delete(c)
    }

//    fun deleteAllCards() {
//        repository.deleteAllCards()
//    }

    fun fetchRandomTarot(n: Int) {
        resetSelector()
        repository.fetchRandomTarot(n)
    }

    fun resetSelector() {
        iSelected.value = 0
    }

    fun getTarotCardList(): LiveData<List<TarotCard>?> {
        return cardList
    }

    fun fetchCardImage(position: Int) {
        repository.fetchCardImage(position)
    }

    fun getISelected(): LiveData<Int> {
        return iSelected
    }

    fun setiSelected(position: Int) {
        iSelected.value = position
    }

    fun getCardImage(curCard: TarotCard?): Drawable {
        return repository.getCardImage(curCard)
    }
}