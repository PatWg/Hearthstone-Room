package fr.isep.ii3510.hearthstonecardsdatabase.service;


import java.util.List;

import fr.isep.ii3510.hearthstonecardsdatabase.db.entity.Card;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CardService {
    public static final String URL = "https://boiling-shelf-89461.herokuapp.com/";

    @GET("cards/")
    Call<CardResponse> getCards();
}
