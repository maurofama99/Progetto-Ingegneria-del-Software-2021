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
    private ArrayList<Resource> checkAvailabilityResources = new ArrayList<>();

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
        ArrayList<Resource> resourcesAfterExtra = new ArrayList<>();
        ArrayList<Resource> resources = new ArrayList<>();
        for (Resource resource : resourcesToRemove)
            resources.add((Resource) resource.clone());

        if (!checkAvailabilityWarehouse(resources))
            throw new NoSuchElementException("You do not have enough resources");

        else {
            for (Resource resource : removeResourcesFromExtraDepot(resources))
                resourcesAfterExtra.add((Resource) resource.clone());

            if (getDepot().checkAvailabilityDepot(resourcesAfterExtra) != null) {
                for (Resource resource : getDepot().checkAvailabilityDepot(resourcesAfterExtra)) {
                    getStrongBox().removeResourceStrongBox(resource);
                }
            }
            for (Resource res : resourcesAfterExtra) {
                for (int i = 0; i < 3; i++) {
                    if (getDepot().getFloors().get(i).isPresent() && getDepot().getFloors().get(i).get().getType().equals(res.getType())) {
                        if (res.getQnt() > getDepot().getFloors().get(i).get().getQnt()) {
                            getDepot().getFloors().set(i, Optional.empty());
                        }
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
        checkAvailabilityResources = getDepot().checkAvailabilityDepot(resources);
        if (checkAvailabilityResources != null){
            for (Resource resource : checkAvailabilityResources) {
                if (!getStrongBox().checkAvailabilityStrongBox(resource)){
                    return false;
                }
            }
        }
        return true;
    }


    public ArrayList<Resource> removeResourcesFromExtraDepot(ArrayList<Resource> resourcesToRemove) throws CloneNotSupportedException {
        boolean checked = false;
        ArrayList<Resource> missingResources = new ArrayList<>();
        ArrayList<Resource> resources = new ArrayList<>();
        for (Resource resource : resourcesToRemove)
            resources.add((Resource) resource.clone());

        for (int j=0; j<resources.size(); j++) {
            for (int k = 0; k < 2; k++) {
                if (getDepot().getExtraFloors().get(k).isPresent() && resources.get(j).getType().equals(getDepot().getExtraFloors().get(k).get().getType())) {
                    if (resources.get(j).getQnt() <= getDepot().getExtraFloors().get(k).get().getQnt()) {
                        getDepot().getExtraFloors().get(k).get().setQnt(getDepot().getExtraFloors().get(k).get().getQnt() - resources.get(j).getQnt());
                    }
                    else {
                        missingResources.add(new Resource(resources.get(j).getQnt() - getDepot().getExtraFloors().get(k).get().getQnt(), resources.get(j).getType()));
                        getDepot().getExtraFloors().get(k).get().setQnt(0);

                    }
                    checked=true;
                }
            }
            if (!checked) {
                missingResources.add(resources.get(j));
            }
            checked = false;
        }

        if (missingResources.size() == 0) return null;
        else return missingResources;
    }


}

