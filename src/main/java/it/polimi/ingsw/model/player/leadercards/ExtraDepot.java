package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Class of the leader cards that act as an extra floor to put resources in.
 */
public class ExtraDepot implements LeaderEffect {

    private Resource toStore;
    private ArrayList<Resource> resourceRequired;

    //aggiunge un second floor di un certo tipo di risorsa
    /**
     * Add a second floor
     * @param player who will have the extra floor
     */
    @Override
    public void doEffect(Player player) {
        //aggiunge un second floor

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
