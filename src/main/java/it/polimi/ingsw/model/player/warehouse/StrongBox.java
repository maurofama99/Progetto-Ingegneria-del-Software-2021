package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.util.ArrayList;

public class StrongBox {
    private Resource[] storedResources;

    public StrongBox(Resource[] storedResources) {
        this.storedResources = storedResources;
    }

    public Resource[] getStoredResources() {
        return storedResources;
    }

    public void setStoredResources(Resource[] storedResources) {
        this.storedResources = storedResources;
    }

    public void addResourceToStrongBox(Resource resourceToAdd){
        if (resourceToAdd.getType().equals(ResourceType.COIN))
            storedResources[0].setQnt(resourceToAdd.getQnt() +  storedResources[0].getQnt());
        else if (resourceToAdd.getType().equals(ResourceType.SERVANT))
            storedResources[1].setQnt(resourceToAdd.getQnt() +  storedResources[1].getQnt());
        else if (resourceToAdd.getType().equals(ResourceType.SHIELD))
            storedResources[2].setQnt(resourceToAdd.getQnt() +  storedResources[2].getQnt());
        else if (resourceToAdd.getType().equals(ResourceType.STONE))
            storedResources[3].setQnt(resourceToAdd.getQnt() +  storedResources[3].getQnt());
        else
            System.out.println("Error in reading type of resource");
    }


    public void removeResourceToStrongBox(Resource resourceToRemove){
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


    public boolean checkAvailabilityStrongBox(Resource resourceToCheck){

        if (resourceToCheck.getType().equals(ResourceType.COIN))
            return resourceToCheck.getQnt() >= storedResources[0].getQnt();

        else if (resourceToCheck.getType().equals(ResourceType.SERVANT))
            return resourceToCheck.getQnt() >= storedResources[1].getQnt();

        else if (resourceToCheck.getType().equals(ResourceType.SHIELD))
            return resourceToCheck.getQnt() >= storedResources[2].getQnt();

        else if (resourceToCheck.getType().equals(ResourceType.STONE))
            return resourceToCheck.getQnt() >= storedResources[3].getQnt();
        else
            return false;

    }

    //mancano metodi che aggiungono e rimuovono più di un tipo di risorsa
}

