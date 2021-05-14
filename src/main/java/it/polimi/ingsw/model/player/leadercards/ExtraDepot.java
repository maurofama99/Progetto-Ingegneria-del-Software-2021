package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Class of the leader cards that act as an extra floor to put resources in.
 */
public class ExtraDepot extends LeaderEffect implements Serializable {

    private ResourceType toStore;
    private ResourceType resourceRequired;

    public ExtraDepot(ResourceType toStore, ResourceType resourceRequired) {
        super(EffectType.EXTRADEPOT);
        this.toStore = toStore;
        this.resourceRequired = resourceRequired;
    }

    @Override
    public Object getObject() {
        return toStore;
    }

    @Override
    public Object getRequirements() {
        return resourceRequired;
    }

    /**
     * Checks if the player can actually place the leader card
     * @param player owner of the card
     * @return true if the card can be placed, false if not
     */
    @Override
    public boolean checkRequirementsLeaderCard(Player player) {
        int cont = 0;
        switch (resourceRequired) {
            case COIN:
                cont += player.getPersonalBoard().getWarehouse().getStrongBox().getStoredResources()[0].getQnt();
                for (int i =0; i<3; i++){
                    if (player.getPersonalBoard().getWarehouse().getDepot().getFloors().get(i).isPresent()
                    && player.getPersonalBoard().getWarehouse().getDepot().getFloors().get(i).get().getType().equals(ResourceType.COIN)){
                        cont += player.getPersonalBoard().getWarehouse().getDepot().getFloors().get(i).get().getQnt();
                    }
                }
                if (cont >= 5) return true;
                break;

            case SERVANT:
                cont += player.getPersonalBoard().getWarehouse().getStrongBox().getStoredResources()[1].getQnt();
                for (int i =0; i<3; i++){
                    if (player.getPersonalBoard().getWarehouse().getDepot().getFloors().get(i).isPresent()
                            && player.getPersonalBoard().getWarehouse().getDepot().getFloors().get(i).get().getType().equals(ResourceType.SERVANT)){
                        cont += player.getPersonalBoard().getWarehouse().getDepot().getFloors().get(i).get().getQnt();
                    }
                }
                if (cont >= 5) return true;
                break;
            case SHIELD:
                cont += player.getPersonalBoard().getWarehouse().getStrongBox().getStoredResources()[2].getQnt();
                for (int i =0; i<3; i++){
                    if (player.getPersonalBoard().getWarehouse().getDepot().getFloors().get(i).isPresent()
                            && player.getPersonalBoard().getWarehouse().getDepot().getFloors().get(i).get().getType().equals(ResourceType.SHIELD)){
                        cont += player.getPersonalBoard().getWarehouse().getDepot().getFloors().get(i).get().getQnt();
                    }
                }
                if (cont >= 5) return true;
                break;
            case STONE:
                cont += player.getPersonalBoard().getWarehouse().getStrongBox().getStoredResources()[3].getQnt();
                for (int i =0; i<3; i++){
                    if (player.getPersonalBoard().getWarehouse().getDepot().getFloors().get(i).isPresent()
                            && player.getPersonalBoard().getWarehouse().getDepot().getFloors().get(i).get().getType().equals(ResourceType.STONE)){
                        cont += player.getPersonalBoard().getWarehouse().getDepot().getFloors().get(i).get().getQnt();
                    }
                }
                if (cont >= 5) return true;
                break;
        }
        return false;
    }

}
