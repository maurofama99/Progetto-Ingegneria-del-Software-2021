package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Class of all player's place to store resources in general
 */
public class Warehouse {

    private Depot depot;
    private StrongBox strongBox;

    public Warehouse(Depot depot, StrongBox strongBox) {
        this.depot = depot;
        this.strongBox = strongBox;
    }

    public Depot getDepot() {
        return depot;
    }

    public StrongBox getStrongBox() {
        return strongBox;
    }


    /**
     * It removes resources when needed by the player.
     * @param resourcesToRemove Resources to remove from warehouse.
     */

    public void removeResources(ArrayList<Resource> resourcesToRemove) {
        int i;

        for (Resource res : resourcesToRemove) {
            for (i = 0; i < 3; i++) {
                if (getDepot().getFloors().get(i).isPresent() && getDepot().getFloors().get(i).get().getType().equals(res.getType())) {
                    if (res.getQnt() > getDepot().getFloors().get(i).get().getQnt()) {
                        if (getStrongBox().checkAvailabilityStrongBox(res))
                            getStrongBox().removeResourceStrongBox(res);
                        else
                            throw new NoSuchElementException("Resource " + res + " not Available");

                        getDepot().getFloors().set(i, Optional.empty());
                    } else {
                        getDepot().getFloors().get(i).get().setQnt(getDepot().getFloors().get(i).get().getQnt() - res.getQnt());
                        if (getDepot().getFloors().get(i).get().getQnt() == 0) {
                            getDepot().getFloors().set(i, Optional.empty());
                        }
                    }
                }
            }


            if (getStrongBox().checkAvailabilityStrongBox(res))
                getStrongBox().removeResourceStrongBox(res);
            else
                throw new NoSuchElementException("Resource " + res + " not Available");
        }
    }
}


