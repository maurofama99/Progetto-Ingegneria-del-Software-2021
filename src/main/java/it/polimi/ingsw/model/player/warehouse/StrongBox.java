package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.util.ArrayList;

public class StrongBox {
    //lo strongbox è un array delle 4 risorse in ordine alfabetico inizializzate con qnt = 0;
    private Resource[] storedResources = new Resource[4];
    private boolean available;

    public StrongBox() {
        storedResources[0] = new Resource(0, ResourceType.COIN);
        storedResources[1] = new Resource(0, ResourceType.SERVANT);
        storedResources[2] = new Resource(0, ResourceType.SHIELD);
        storedResources[3] = new Resource(0, ResourceType.STONE);
        available = false;
    }

    public Resource[] getStoredResources() {
        return storedResources;
    }

    public void setStoredResources(Resource[] storedResources) {
        this.storedResources = storedResources;
    }

    //since  StrongBox has no limit in terms of how many resources can be stored.
    // It does not need to check anything. This method adds resources to Strongbox

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

    //This method is used to remove resources from the player's Strongbox. When the Resource
    // is not available it prints that there are not enough Resources

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
            System.out.println("There are not enough resources in StrongBox");
        }

    //this method checks if a type of Resource is available in Strongbox.
    public boolean checkAvailabilityStrongBox(Resource resourceToCheck){
        if (resourceToCheck.getType().equals(ResourceType.COIN))
            available = (resourceToCheck.getQnt() <= storedResources[0].getQnt());

        else if (resourceToCheck.getType().equals(ResourceType.SERVANT))
            available = (resourceToCheck.getQnt() <= storedResources[1].getQnt());

        else if (resourceToCheck.getType().equals(ResourceType.SHIELD))
            available =  (resourceToCheck.getQnt() <= storedResources[2].getQnt());

        else if (resourceToCheck.getType().equals(ResourceType.STONE))
            available =  (resourceToCheck.getQnt() <= storedResources[3].getQnt());

        return available;
    }

}

