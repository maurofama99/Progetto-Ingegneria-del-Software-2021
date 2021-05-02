package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;

/**
 * Class for the leader cards that add a production power
 */
public class AddProduction extends LeaderEffect{

    private DevCard cardRequired;
    private Resource resourceRequired;

    public AddProduction(DevCard cardRequired, Resource resourceRequired) {
        super(EffectType.ADDPRODUCTION);
        this.cardRequired = cardRequired;
        this.resourceRequired = resourceRequired;
    }

    @Override
    public Object getObject() {
        return resourceRequired;
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
                if (player.getPersonalBoard().getSlots()[i].getCards().get(k).getLevel() == cardRequired.getLevel())
                    return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "AddProduction{" +
                "cardColorRequired=" + cardColorRequired +
                ", resourceRequired=" + resourceRequired +
                '}';
    }

    /*
     AddProduction
    1 servo --> una scelta + 1 faithP, costo: una blu livello 2 ,   4pv
    1 pietra --> una scelta + 1 faithP, costo: una viola livello 2,  4pv
    1 moneta --> una scelta + 1 faithP, costo: una verde livello 2, 4pv
    1 scudo --> una scelta + 1faithP, costo: una verde livello 2, 4pv

    */




}
