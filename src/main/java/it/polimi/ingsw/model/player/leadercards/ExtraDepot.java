package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Class of the leader cards that act as an extra floor to put resources in.
 */
public class ExtraDepot extends LeaderEffect {

    private Resource toStore;
    private ArrayList<Resource> resourceRequired;

    public ExtraDepot(Resource toStore, ArrayList<Resource> resourceRequired) {
        super(EffectType.EXTRADEPOT);
        this.toStore = toStore;
        this.resourceRequired = resourceRequired;
    }

    @Override
    public Object getObject() {
        return toStore.getType();
    }

    /**
     * Checks if the player can actually place the leader card
     * @param player owner of the card
     * @return true if the card can be placed, false if not
     */
    @Override
    public boolean checkRequirementsLeaderCard(Player player) {
        try {
            player.getPersonalBoard().getWarehouse().removeResources(resourceRequired);
            return true;
        } catch (NoSuchElementException ex){
            return false;
        }


    }
}
