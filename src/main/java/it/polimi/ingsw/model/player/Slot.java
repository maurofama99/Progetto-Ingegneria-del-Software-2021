package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.devcard.DevCard;

import java.util.ArrayList;

public class Slot{
    private ArrayList<DevCard> cards;
    //creata una classe enum per lo slot number
    private SlotNumber slotNumber;


    public Slot(SlotNumber slotNumber) {
        this.slotNumber = slotNumber;
    }

    public DevCard getShowedCard(){
        return this.cards.get(this.cards.size()-1);
    }

    //piazza la dev card in questo slot
    public void PlaceDevCard(DevCard devCard) throws IllegalAccessException {
        if ((devCard.getLevel() == this.getShowedCard().getLevel() + 1) || cards.isEmpty())
        this.cards.add(devCard);
        else throw new IllegalAccessException("Can't place " + devCard.toString() + "here, " + devCard.toString() + "level is " + devCard.getLevel() +
                                                ", but you need a level " + this.getShowedCard().getLevel() + 1 + "card");
    }

    /* ******************************************************
     E' NECESSARIO METTERE IL METODO CHE AGGIUNGE I VICTORY
     POINTS NEL CONTROLLER (STATO BUYDEVCARD)
     ********************************************************/



}
