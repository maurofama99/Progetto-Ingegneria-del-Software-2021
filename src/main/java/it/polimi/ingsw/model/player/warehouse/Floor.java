package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.util.Optional;

public abstract class Floor {
    //se la review da un warning ignoralo
    protected Optional<Resource> storedResource;
    private final int space ;

    public Floor( int space) {
        this.storedResource = Optional.empty();
        this.space = space;
    }

    public void setStoredResource(Optional<Resource> storedResource) {
        this.storedResource = storedResource;
    }

    public Optional<Resource> getStoredResource() {
        return storedResource;
    }


    /* ****************************************************************
     NON DEVE MAI ESSERE POSSIBILE AGGIUNGERE WHITERESOURCE AI FLOOR
     ******************************************************************/
    public void setStoredResource(Resource storedResource) {
        if (storedResource.getType().equals(ResourceType.WHITERESOURCE)) throw new IllegalArgumentException("Can't place white resource here");
        if (storedResource.getType().equals(ResourceType.FAITHPOINT)) throw new IllegalArgumentException("Can't place faith point here");
        this.storedResource = Optional.of(storedResource);
    }

    public abstract boolean differentResources(Resource resourceToPlace);

    //checks if there is space in the different floors for the extra resource that the player needs to place
    public boolean checkCorrectPlacement(Resource resourceToPlace) {
        if (!differentResources(resourceToPlace)) {
            return false;
        }
        //se c'è abbastanza spazio sul piano se la risorsa c'è gia
        if (storedResource.isPresent()){
            if(storedResource.get().getType() == resourceToPlace.getType()) {
                return resourceToPlace.getQnt() <= (space - storedResource.get().getQnt());
            }
            else return false;
        }//then I need to check that there is a free floor with enough space
        return resourceToPlace.getQnt() <= space;
    }

}

