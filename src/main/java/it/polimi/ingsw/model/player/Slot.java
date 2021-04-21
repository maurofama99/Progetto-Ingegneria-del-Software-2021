package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.devcard.DevCard;

import java.util.ArrayList;

/**
 * Class of the slots in the personal board, where the dev cards are placed
 */
public class Slot{
    private ArrayList<DevCard> cards = new ArrayList<>();
    //created enum class for slot number
    private SlotNumber slotNumber;


    public Slot(SlotNumber slotNumber) {
        this.slotNumber = slotNumber;
    }

    public ArrayList<DevCard> getCards() {
        return cards;
    }

    public DevCard getShowedCard(){
        return this.cards.get(this.cards.size()-1);
    }

    //place dev card in this slot forse qui bisogna richiamare il fatto che toglie le risorse quando compra la carta
    public void placeDevCard(DevCard devCard) throws IllegalAccessException {
        if ((cards.isEmpty() && (devCard.getLevel() == 1) || devCard.getLevel()-1 == getShowedCard().getLevel()))
        this.cards.add(devCard);
        else throw new IllegalAccessException("Can't place " + devCard + "here, " + devCard + "level is " + devCard.getLevel() +
                                                ", but you need a level " + this.getShowedCard().getLevel() + 1 + "card");
    }


    //TODO E' NECESSARIO METTERE IL METODO CHE AGGIUNGE I VICTORY POINTS NEL CONTROLLER (STATO BUYDEVCARD)



}
