package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Class of all player's place to store resources in general
 */
public class Warehouse{

    private final Depot depot;
    private final StrongBox strongBox;

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
    public void removeResources(ArrayList<Resource> resourcesToRemove) throws CloneNotSupportedException {

        ArrayList<Resource> resources = new ArrayList<>();
        for (Resource resource : resourcesToRemove)
            resources.add((Resource) resource.clone());

        if (!checkAvailabilityWarehouse(resources))
            throw new NoSuchElementException("You do not have enough resources");
        else {
            if (getDepot().checkAvailabilityDepot(resources) != null) {
                for (Resource resource : getDepot().checkAvailabilityDepot(resources)) {
                    getStrongBox().removeResourceStrongBox(resource);
                }
            }
            for (Resource res : resources) {
                for (int i = 0; i < 3; i++) {
                    if (getDepot().getFloors().get(i).isPresent() && getDepot().getFloors().get(i).get().getType().equals(res.getType())) {
                        if (res.getQnt() > getDepot().getFloors().get(i).get().getQnt())
                            getDepot().getFloors().set(i, Optional.empty());

                        else {
                            getDepot().getFloors().get(i).get().setQnt(getDepot().getFloors().get(i).get().getQnt() - res.getQnt());
                            if (getDepot().getFloors().get(i).get().getQnt() == 0)
                                getDepot().getFloors().set(i, Optional.empty());
                        }
                    }
                }
            }
        }

    }

    /**
     * check the availability of resources in the whole warehouse.
     * @param resources resources that need to be checked.
     * @return true if and only if all of the resources are available.
     */
    public boolean checkAvailabilityWarehouse (ArrayList<Resource> resources) throws CloneNotSupportedException {
        if (getDepot().checkAvailabilityDepot(resources) != null){
            for (Resource resource : getDepot().checkAvailabilityDepot(resources)) {
                if (!getStrongBox().checkAvailabilityStrongBox(resource)) {
                    return false;
                }
            }
        }
        return true;
    }
}

