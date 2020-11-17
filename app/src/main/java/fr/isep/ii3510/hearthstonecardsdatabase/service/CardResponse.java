package fr.isep.ii3510.hearthstonecardsdatabase.service;

import java.util.List;

import fr.isep.ii3510.hearthstonecardsdatabase.db.entity.Card;

public class CardResponse {
    private String status;
    private List<Card> results;

    public CardResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Card> getResults() {
        return results;
    }

    public void setResults(List<Card> results) {
        this.results = results;
    }
}
