package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Class of the leader cards that act as an extra floor to put resources in.
 */
public class ExtraDepot implements LeaderEffect {

    private Resource toStore;
    private ArrayList<Resource> resourceRequired;

    public Resource getToStore() {
        return toStore;
    }

    //aggiunge un second floor di un certo tipo di risorsa
    /**
     * Add an extra floor with two spaces capacity
     * @param player who will have the extra floor
     */
    @Override
    public void doEffect(Player player) {
        if(player.getPersonalBoard().getWarehouse().getDepot().getExtraFloors().get(0).isEmpty()){
            player.getPersonalBoard().getWarehouse().getDepot().getExtraFloors().set(0, Optional.of(new Resource(0,toStore.getType())));
        } else {
            player.getPersonalBoard().getWarehouse().getDepot().getExtraFloors().set(1, Optional.of(new Resource(0,toStore.getType())));
        }
    }

    //necessarie 5 risorse(di un unico tipo) per attivarla
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
