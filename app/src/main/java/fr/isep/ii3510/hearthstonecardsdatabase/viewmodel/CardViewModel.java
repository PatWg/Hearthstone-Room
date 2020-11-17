package fr.isep.ii3510.hearthstonecardsdatabase.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fr.isep.ii3510.hearthstonecardsdatabase.CardRepository;
import fr.isep.ii3510.hearthstonecardsdatabase.db.entity.Card;

public class CardViewModel extends AndroidViewModel {
    private CardRepository cardRepository;
    private LiveData<List<Card>> cards;

    public CardViewModel(@NonNull Application application) {
        super(application);
        cardRepository = new CardRepository(application);
        cards = new MutableLiveData<>();
    }

    public void initialiseDatabase() {
        cardRepository.initialiseDatabase();
    }

    public LiveData<List<Card>> getCards() {
        cards = cardRepository.getCards();
        return cards;
    }
}
