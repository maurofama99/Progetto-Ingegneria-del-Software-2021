package it.polimi.ingsw.model;

import it.polimi.ingsw.model.devcard.DevCard;

import java.util.HashSet;

public class Deck {
    private DevCard[][] showedDeck;
    private HashSet<DevCard> fullDeck;

    public DevCard[][] getShowedDeck() {
        return showedDeck;
    }

    public void setShowedDeck(DevCard[][] showedDeck) {
        this.showedDeck = showedDeck;
    }

    public HashSet<DevCard> getFullDeck() {
        return fullDeck;
    }

    public void setFullDeck(HashSet<DevCard> fullDeck) {
        this.fullDeck = fullDeck;
    }

    public void removeCard(){

    }

    public void addTopCard(){

    }
}
