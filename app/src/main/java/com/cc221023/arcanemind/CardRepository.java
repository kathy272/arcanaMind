package com.cc221023.arcanemind;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.lifecycle.LiveData;

import com.cc221023.arcanemind.data.TarotDao;
import com.cc221023.arcanemind.data.TarotDatabase;
import com.cc221023.arcanemind.utils.JsonUtils;
import com.cc221023.arcanemind.utils.NetworkUtils;

import java.util.HashMap;
import java.util.List;


//import android.arch.lifecycle.LiveData;
//import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class CardRepository {
    private static final String TAG = CardRepository.class.getSimpleName();

    private TarotDao tarotDao;
    public LiveData<List<TarotCard>> tarotCardList;
    private static HashMap<String, Drawable> cardImages = new HashMap<>();


    public CardRepository(Application application) {
        TarotDatabase db = TarotDatabase.getInstance(application);
        tarotDao = db.tarotDao();
        tarotCardList = tarotDao.getAllTarotCards();
    }

    // == API Functions ==
    public void insert(TarotCard c) {
        new InsertCardAsyncTask(tarotDao).execute(c);
    }

    public void update(TarotCard c) {
        new UpdateCardAsyncTask(tarotDao).execute(c);
    }

    public void delete(TarotCard c) {
        new DeleteCardAsyncTask(tarotDao).execute(c);
    }

//    public void deleteAllCards() {
//        new DeleteAllCardsAsyncTask(tarotDao).execute();
//    }

    public void fetchRandomTarot(int n) {
        new FetchNCardsAsyncTask(tarotDao).execute(n);
    }

    public void fetchCardImage(int position) {
        TarotCard c = (TarotCard) tarotCardList.getValue().get(position);
        new FetchImageAsyncTask(tarotDao).execute(c);
    }

    public LiveData<List<TarotCard>> getTarotCardList() {
        return tarotCardList;
    }

    public Drawable getCardImage(TarotCard curCard) {
        return cardImages.get(curCard.getNameShort());
    }

    // === Background Threads ===
    public static class InsertCardAsyncTask extends AsyncTask<TarotCard, Void, Void> {
        private TarotDao tarotDao;

        private InsertCardAsyncTask(TarotDao tarotDao) {
            this.tarotDao = tarotDao;
        }

        @Override
        protected Void doInBackground(TarotCard... TarotCards) {
            tarotDao.insert(TarotCards[0]);
            return null;
        }
    }

    public static class UpdateCardAsyncTask extends AsyncTask<TarotCard, Void, Void> {
        private TarotDao tarotDao;

        private UpdateCardAsyncTask(TarotDao tarotDao) {
            this.tarotDao = tarotDao;
        }

        @Override
        protected Void doInBackground(TarotCard... TarotCards) {
            //TarotDao.update(TarotCards[0]);
            tarotDao.update(TarotCards[0]);
            return null;
        }
    }

    public static class DeleteCardAsyncTask extends AsyncTask<TarotCard, Void, Void> {
        private TarotDao tarotDao;

        private DeleteCardAsyncTask(TarotDao tarotDao) {
            this.tarotDao = tarotDao;
        }

        @Override
        protected Void doInBackground(TarotCard... TarotCards) {
            tarotDao.delete(TarotCards[0]);
            return null;
        }
    }

//    public static class DeleteAllCardsAsyncTask extends AsyncTask<Void, Void, Void> {
//        private CardDao cardDao;
//
//        private DeleteAllCardsAsyncTask(CardDao cardDao) {
//            this.cardDao = cardDao;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            cardDao.deleteAllCards();
//            return null;
//        }
//    }

    // === NETWORK LOGIC TO RETRIEVE CARD DATA ===
    // Retrieves card data from tarot-api by GitHub user @ekelen: https://github.com/ekelen/tarot-api
    private static class FetchNCardsAsyncTask extends AsyncTask<Integer, Void, Void> {
        private static final String URI_BASE = "https://rws-cards-api.herokuapp.com/api/v1/cards/";
        TarotDao tarotDao;

        private FetchNCardsAsyncTask(TarotDao tarotDao) {
            this.tarotDao = tarotDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            // BUILD URL
            String numCards = String.valueOf(integers[0]);
            Uri uri = Uri.parse(URI_BASE).buildUpon()
                    .appendPath("random")
                    .appendQueryParameter("n", numCards)
                    .build();
            URL url = null;
            try {
                url = new URL(uri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: MalformedURL");
            }

            // QUERY FOR CARDS OVER NETWORK
            if (url == null) return null;
            try {
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
                List<TarotCard> TarotCards = JsonUtils.JsonUtils.parseCardsFromJson(jsonResponse);
                for (TarotCard c : TarotCards) {
                    tarotDao.insert(c);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: I/O err");
            } catch (JSONException e) {
                Log.e(TAG, "doInBackground: JSON err");
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class FetchImageAsyncTask extends AsyncTask<TarotCard, Void, Void> {
        private TarotDao tarotDao;
        public FetchImageAsyncTask(TarotDao tarotDao) {
            this.tarotDao = tarotDao;
        }

        @Override
        protected Void doInBackground(TarotCard... TarotCards) {
            if (!cardImages.containsKey(TarotCards[0].getNameShort())) {
                Drawable cardImage;
                String nameShort = TarotCards[0].getNameShort();
                String strImage = String.format("https://www.sacred-texts.com/tarot/pkt/img/%s.jpg", nameShort);
                try {
                    cardImage = Drawable.createFromStream((InputStream) new URL(strImage).getContent(), "src");
                    if (cardImage != null) {
                        cardImages.put(nameShort, cardImage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            TarotCards[0].setHasImage(true);
            tarotDao.update(TarotCards[0]);
            return null;
        }
    }
}