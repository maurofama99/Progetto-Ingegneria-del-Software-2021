package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.devcard.DevCard;

import java.util.ArrayList;

public class Slot{
    private ArrayList<DevCard> cards = new ArrayList<>();
    //created enum class for slot number
    private SlotNumber slotNumber;


    public Slot(SlotNumber slotNumber) {
        this.slotNumber = slotNumber;
    }

    public DevCard getShowedCard(){
        return this.cards.get(this.cards.size()-1);
    }

    //place dev card in this slot
    public void PlaceDevCard(DevCard devCard) throws IllegalAccessException {
        if (cards.isEmpty() || (devCard.getLevel() == this.getShowedCard().getLevel() + 1))
        this.cards.add(devCard);
        else throw new IllegalAccessException("Can't place " + devCard.toString() + "here, " + devCard.toString() + "level is " + devCard.getLevel() +
                                                ", but you need a level " + this.getShowedCard().getLevel() + 1 + "card");
    }


    //TODO E' NECESSARIO METTERE IL METODO CHE AGGIUNGE I VICTORY POINTS NEL CONTROLLER (STATO BUYDEVCARD)



}
