package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.devcard.Color;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;

/**
 * Leader cards that give extra resources when getting white resources from the market
 */
public class SwapWhite extends LeaderEffect {

    private ArrayList<Color> cardColorRequired;
    private Resource newResource;

    public SwapWhite(String effectName, ArrayList<Color> cardColorRequired, Resource newResource) {
        super(EffectType.SWAPWHITE);
        this.cardColorRequired = cardColorRequired;
        this.newResource = newResource;
    }


    @Override
    public Object getObject() {
        return newResource;
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
