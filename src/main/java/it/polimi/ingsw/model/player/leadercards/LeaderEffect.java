package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.io.Serializable;

public abstract class LeaderEffect implements Serializable {

    private EffectType effectType;

    public LeaderEffect(EffectType effectType) {
        this.effectType = effectType;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    /**
     * Special getter
     * @return a characteristic of the leader card effect.
     */
    public abstract Object getObject();

    /**
     * Special getter
     * @return requirements of leader card effect.
     */
    public abstract Object getRequirements();

    /**
     * Checks if the player can actually place the leader card
     * @param player owner of the card
     * @return true if the card can be placed, false if not
     */
    public abstract boolean checkRequirementsLeaderCard(Player player);

    @Override
    public String toString() {
        switch (effectType){
            case SWAPWHITE:
                return "SWAP-" + ((Resource)getObject()).toStringGui();
            case EXTRADEPOT:
                Resource resDepot = new Resource(1, (ResourceType)getObject());
                return "DEPOT-" + resDepot.toStringGui();
            case DISCOUNT:
                Resource resDiscount = new Resource(1, (ResourceType)getObject());
                return "DISCOUNT-" + resDiscount.toStringGui();
            case ADDPRODUCTION:
                return "PRODUCTION-"+((Resource)getObject()).toStringGui();
            default:
                throw new IllegalStateException("Unexpected value: " + getEffectType());
        }
    }
}
