package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.devcard.DevCard;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Class that models a slot in the personal board. Development cards are placed here.
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

    /**
     * Getter of the last card placed in a slot.
     * @return null if the slot is empty.
     */
    public DevCard getShowedCard(){
        if (this.cards.size() == 0){
            return null;
        }
        return this.cards.get(this.cards.size()-1);
    }

    /**
     * Places development card in the slot.
     * @param devCard development card to place.
     * @return true if and only if the player has the requirements to place it.
     * @throws IllegalAccessException if the player can not place the development card.
     */
    public boolean placeDevCard(DevCard devCard) throws IllegalAccessException {

        if (cards.isEmpty() && devCard.getLevel() != 1) {
            throw new IllegalAccessException("Can't place " + devCard + "here, " + devCard + "level is " + devCard.getLevel() + ", but slot is empty!\n");

        }
        else if ((cards.isEmpty() && (devCard.getLevel() == 1) || devCard.getLevel()-1 == getShowedCard().getLevel())){
            this.cards.add(devCard);
            return true;
        }
        else {
            throw new IllegalAccessException("Can't place " + devCard + "here, " + devCard + "level is " + devCard.getLevel() +
                    ", but you need a level " + this.getShowedCard().getLevel() + 1 + "card");
        }

    }




}
