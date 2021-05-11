package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Class of all player's place to store resources in general
 */
public class Warehouse implements Serializable {

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
     *
     * @param resourcesToRemove Resources to remove from warehouse.
     */

    public void removeResources(ArrayList<Resource> resourcesToRemove) throws CloneNotSupportedException {
        ArrayList<Resource> resourcesInFloor = new ArrayList<>();
        ArrayList<Resource> resources = new ArrayList<>();
        for (Resource resource : resourcesToRemove) resources.add((Resource) resource.clone());

        for (Resource res : resources) {
            for (int i = 0; i < 3; i++) {
                if (getDepot().getFloors().get(i).isPresent() && getDepot().getFloors().get(i).get().getType().equals(res.getType())) {
                    if (res.getQnt() > getDepot().getFloors().get(i).get().getQnt()) {
                        res.setQnt(res.getQnt()-getDepot().getFloors().get(i).get().getQnt());
                        if (getStrongBox().checkAvailabilityStrongBox(res)) {
                            getStrongBox().removeResourceStrongBox(res);
                            resourcesInFloor.add(res);
                        } else
                            throw new NoSuchElementException("Resource " + res + " not Available");
                        getDepot().getFloors().set(i, Optional.empty());
                    } else {
                        getDepot().getFloors().get(i).get().setQnt(getDepot().getFloors().get(i).get().getQnt() - res.getQnt());
                        if (getDepot().getFloors().get(i).get().getQnt() == 0) {
                            getDepot().getFloors().set(i, Optional.empty());
                        }
                        resourcesInFloor.add(res);
                    }
                }
            }
            if (!resourcesInFloor.contains(res)) {
                if (getStrongBox().checkAvailabilityStrongBox(res))
                    getStrongBox().removeResourceStrongBox(res);
                else
                    throw new NoSuchElementException("Resource " + res + " not Available");
            }

        }
    }

    @Override
    public String toString() {
        return "DEPOT: \n " + depot.toString() +
                "\n\nSTRONGBOX"+ strongBox.toString();
    }
}