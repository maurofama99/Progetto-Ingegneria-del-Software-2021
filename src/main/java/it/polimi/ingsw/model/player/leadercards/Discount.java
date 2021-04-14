package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.devcard.Color;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;

/**
 * Class for the leader cards that give a discount when buying a dev. card
 */
public class Discount implements LeaderEffect {
    private Resource discountOn;
    private ArrayList<Color> cardColorRequired;

    /**
     * Actively changes the cost a dev. card
     * @param player owner of the leader cared
     */
    @Override
    public void doEffect(Player player) {
        //cambia il costo di una dev card
        //devo fare un setRequirementDevCard nelle devCard che richiedono questo tipo di risorsa
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
                for (k=0;k<3; k++){
                    if (player.getPersonalBoard().getSlots()[i].getCards().get(k).getCardColor().equals(cardColor))
                        checkColor.add(cardColor);
                }
            }
        }

        return checkColor.containsAll(cardColorRequired);
    }
}
