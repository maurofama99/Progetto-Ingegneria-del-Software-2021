package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.devcard.DevCard;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Class of the slots in the personal board, where the dev cards are placed
 */
public class Slot implements Serializable{
    private ArrayList<DevCard> cards = new ArrayList<>();
    private int slotNumber;


    public Slot(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public ArrayList<DevCard> getCards() {
        return cards;
    }

    public DevCard getShowedCard(){
        if (this.cards.size() == 0){
            return null;
        }
        return this.cards.get(this.cards.size()-1);
    }


    public void placeDevCard(DevCard devCard) throws IllegalAccessException {
        if (cards.isEmpty() && devCard.getLevel() != 1)
            throw new IllegalAccessException("Can't place " + devCard + "here, " + devCard + "level is " + devCard.getLevel() +", but slot is empty!\n");

        if ((cards.isEmpty() && (devCard.getLevel() == 1) || devCard.getLevel()-1 == getShowedCard().getLevel())){
            this.cards.add(devCard);
        }
        else throw new IllegalAccessException("Can't place " + devCard + "here, " + devCard + "level is " + devCard.getLevel() +
                                                ", but you need a level " + this.getShowedCard().getLevel() + 1 + "card");
    }




}
