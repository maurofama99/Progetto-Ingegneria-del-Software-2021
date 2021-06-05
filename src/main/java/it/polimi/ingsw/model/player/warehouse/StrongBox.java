package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;


import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * the strongbox is an array of the 4 resources that can be stored, in alfhabetic order and with
 * an initialized quantity of 0. Only productions power store their things here
 */
public class StrongBox{

    private Resource[] storedResources = new Resource[4];

    public StrongBox() {
        this.storedResources[0] = new Resource(20, ResourceType.COIN);
        this.storedResources[1] = new Resource(20, ResourceType.SERVANT);
        this.storedResources[2] = new Resource(20, ResourceType.SHIELD);
        this.storedResources[3] = new Resource(20, ResourceType.STONE);
    }

    public Resource[] getStoredResources() {
        return storedResources;
    }

    /**
     * No check needed, since the strongbox is infinite. This method is simply called
     * when a production is activated to add the resources
     * @param resourcesToAdd what resource is added
     */
    public void addResourceToStrongBox(ArrayList<Resource> resourcesToAdd){
        for (Resource res : resourcesToAdd) {
            if (res.getType().equals(ResourceType.COIN))
                storedResources[0].setQnt(res.getQnt() + storedResources[0].getQnt());
            else if (res.getType().equals(ResourceType.SERVANT))
                storedResources[1].setQnt(res.getQnt() + storedResources[1].getQnt());
            else if (res.getType().equals(ResourceType.SHIELD))
                storedResources[2].setQnt(res.getQnt() + storedResources[2].getQnt());
            else if (res.getType().equals(ResourceType.STONE))
                storedResources[3].setQnt(res.getQnt() + storedResources[3].getQnt());
            else
                System.out.println("Error in reading type of resource");
        }
    }

    /**
     * Method used to remove stuff from the strongbox, to buy or activate productions. If there
     * aren't enough resources, it is printed
     * @param resourceToRemove what resource we remove
     */
    public void removeResourceStrongBox(Resource resourceToRemove){
        if(checkAvailabilityStrongBox(resourceToRemove)) {
            if (resourceToRemove.getType().equals(ResourceType.COIN))
                storedResources[0].setQnt(storedResources[0].getQnt() - resourceToRemove.getQnt());
            else if (resourceToRemove.getType().equals(ResourceType.SERVANT))
                storedResources[1].setQnt(storedResources[1].getQnt() - resourceToRemove.getQnt());
            else if (resourceToRemove.getType().equals(ResourceType.SHIELD))
                storedResources[2].setQnt(storedResources[2].getQnt() - resourceToRemove.getQnt());
            else if (resourceToRemove.getType().equals(ResourceType.STONE))
                storedResources[3].setQnt(storedResources[3].getQnt() - resourceToRemove.getQnt());
        }
        else
           throw new NoSuchElementException("Resource requested is not available");

    }

    /**
     * it checks if a specific type of resource is preset in a quantity >0
      * @param resourceToCheck which resource we are trying to find
     * @return true if present, false if not
     */
    public boolean checkAvailabilityStrongBox(Resource resourceToCheck){
        boolean available = false;
        if (resourceToCheck.getType().equals(ResourceType.COIN))
            available = (resourceToCheck.getQnt() <= storedResources[0].getQnt());

        else if (resourceToCheck.getType().equals(ResourceType.SERVANT))
            available = (resourceToCheck.getQnt() <= storedResources[1].getQnt());

        else if (resourceToCheck.getType().equals(ResourceType.SHIELD))
            available = (resourceToCheck.getQnt() <= storedResources[2].getQnt());

        else if (resourceToCheck.getType().equals(ResourceType.STONE))
            available =  (resourceToCheck.getQnt() <= storedResources[3].getQnt());

        return available;
    }

}

