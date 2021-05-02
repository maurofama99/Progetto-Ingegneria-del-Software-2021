package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.devcard.Color;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.util.ArrayList;

/**
 * Class for the leader cards that give a discount when buying a development card
 */
public class Discount extends LeaderEffect{

    private ResourceType discountOn;
    private ArrayList<Color> cardColorRequired;

    public Discount(ResourceType discountOn, ArrayList<Color> cardColorRequired) {
        super(EffectType.DISCOUNT);
        this.discountOn = discountOn;
        this.cardColorRequired = cardColorRequired;
    }

    @Override
    public Object getObject() {
        return discountOn;
    }

    /**
     * Checks if the player can actually place the leader card
     * @param player owner of the card
     * @return true if the card can be placed, false if not
     */
    @Override
    public boolean checkRequirementsLeaderCard(Player player) {
        ArrayList<Color> checkColor = new ArrayList<>();
        for(Color cardColor: cardColorRequired){
            int i, k;
            for (i=0; i<3; i++){
                for (k=0;k<player.getPersonalBoard().getSlots()[i].getCards().size(); k++){
                    if (player.getPersonalBoard().getSlots()[i].getCards().get(k).getCardColor().equals(cardColor))
                        checkColor.add(cardColor);
                }
            }
        }

        return checkColor.containsAll(cardColorRequired);
    }



    //discount:
    //-1 scudo : una carta blu e una viola, 2pv
    //-1 pietra : una carta verde e una blu, 2pv
    //-1 moneta : una carta gialla e una viola , 2pv
    //-1 servo : una carta gialla e una verde, 2pv


    @Override
    public String toString() {
        return "Discount{" +
                "discountOn=" + discountOn +
                ", cardColorRequired=" + cardColorRequired +
                '}';
    }
}
