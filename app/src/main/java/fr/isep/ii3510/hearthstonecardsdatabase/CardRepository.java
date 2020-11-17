package fr.isep.ii3510.hearthstonecardsdatabase;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import fr.isep.ii3510.hearthstonecardsdatabase.db.CardDatabase;
import fr.isep.ii3510.hearthstonecardsdatabase.db.dao.CardDao;
import fr.isep.ii3510.hearthstonecardsdatabase.db.entity.Card;
import fr.isep.ii3510.hearthstonecardsdatabase.service.CardResponse;
import fr.isep.ii3510.hearthstonecardsdatabase.service.CardService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CardRepository {
    private CardDao cardDao;
    private Application application;
    private LiveData<List<Card>> cards;

    public CardRepository(Application application) {
        CardDatabase db = CardDatabase.getDatabase(application);
        cardDao = db.cardDao();
        this.application = application;
    }

    public LiveData<List<Card>> initialiseDatabase() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CardService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CardService service = retrofit.create(CardService.class);
        service.getCards().enqueue(new Callback<CardResponse>() {
            @Override
            public void onResponse(Call<CardResponse> call, Response<CardResponse> response) {
                List<Card> cards = response.body().getResults();
                insert(cards.toArray(new Card[0]));
            }

            @Override
            public void onFailure(Call<CardResponse> call, Throwable t) {
                Log.d("Retrofit", "Something went wrong.");
            }
        });
        return cards;
    }

    private void insert(Card... cards) {
        Callable<List<Long>> task = () -> cardDao.insert(cards);
        Future<List<Long>> future = CardDatabase.databaseWriteExecutor.submit(task);
        try {
            List<Long> result = future.get();
            SharedPreferences prefs = application.getSharedPreferences(MainActivity.DATABASE_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(MainActivity.DATABASE_NEEDS_INITIALISATION, false);
            editor.apply();
            Log.d("Room", "Insert is finished");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public LiveData<List<Card>> getCards() {
        Callable<LiveData<List<Card>>> task = () -> cardDao.getCards();
        Future<LiveData<List<Card>>> future = CardDatabase.databaseWriteExecutor.submit(task);
        try {
            LiveData<List<Card>> result = future.get();
            cards = result;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return cards;
    }
}
