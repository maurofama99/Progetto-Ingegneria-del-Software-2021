package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.devcard.Color;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;

import java.io.Serializable;

/**
 * Class for the leader card's effect that add a production power.
 */
public class AddProduction extends LeaderEffect implements Serializable {

    private final Color cardColorRequired;
    private final Resource resourceRequired;

    public AddProduction(Color cardColorRequired, Resource resourceRequired) {
        super(EffectType.ADDPRODUCTION);
        this.cardColorRequired = cardColorRequired;
        this.resourceRequired = resourceRequired;
    }

    @Override
    public Object getObject() {
        return resourceRequired;
    }

    @Override
    public Object getRequirements() {
        return cardColorRequired;
    }

    /**
     * Checks if the player can actually place the leader card
     * @param player owner of the card
     * @return true if the card can be placed, false if not
     */

    @Override
    public boolean checkRequirementsLeaderCard(Player player) {
        int i, k;
        for (i=0; i<3; i++){
            for (k=0; k<player.getPersonalBoard().getSlots()[i].getCards().size(); k++){
                if (player.getPersonalBoard().getSlots()[i].getCards().get(k).getLevel() == 2
                        && player.getPersonalBoard().getSlots()[i].getCards().get(k).getCardColor().equals(cardColorRequired))
                    return true;
            }
        }
        return false;
    }
}
