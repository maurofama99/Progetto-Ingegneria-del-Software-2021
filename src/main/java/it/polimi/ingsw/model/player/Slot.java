package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.devcard.DevCard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that models a slot in the personal board. Development cards are placed here.
 */
public class Slot implements Serializable{
    private final ArrayList<DevCard> cards = new ArrayList<>();

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
     * @throws IllegalAccessException if the player can not place the development card.
     */
    public void placeDevCard(DevCard devCard) throws IllegalAccessException {

        if (cards.isEmpty() && devCard.getLevel() != 1) {
            throw new IllegalAccessException("Can't place " + devCard + "here, " + devCard + "level is " + devCard.getLevel() + ", but slot is empty!\n");

        }
        else if ((cards.isEmpty() && (devCard.getLevel() == 1) || devCard.getLevel()-1 == getShowedCard().getLevel())){
            this.cards.add(devCard);
        }
        else {
            throw new IllegalAccessException("Can't place " + devCard + "here, " + devCard + "level is " + devCard.getLevel() +
                    ", but you need a level " + this.getShowedCard().getLevel() + 1 + "card");
        }

    }




}
