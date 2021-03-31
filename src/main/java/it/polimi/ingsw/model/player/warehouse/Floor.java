package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.util.Optional;

public abstract class Floor {
    protected Optional<Resource> storedResource;
    private final int space;

    public Floor(Resource storedResource, int space) {
        this.storedResource = Optional.ofNullable(storedResource);
        this.space = space;
    }

    public Optional<Resource> getStoredResource() {
        return storedResource;
    }

    public int getSpace() {
        return space;
    }

    /******************************************************************
     NON DEVE MAI ESSERE POSSIBILE AGGIUNGERE WHITERESOURCE AI FLOOR
     ******************************************************************/
    public void setStoredResource(Resource storedResource) {
        if (storedResource.getType().equals(ResourceType.WHITERESOURCE)) throw new IllegalArgumentException("Can't place white resource here");
        else this.storedResource = Optional.of(storedResource);
    }


    //checks if there is space in the different floors for the extra resource that the player needs to place
    public boolean checkCorrectPlacement(Resource resourceToPlace) {
        //se c'è abbastanza spazio sul piano se la risorsa c'è gia
        if (storedResource.isPresent() && (storedResource.get().getType() == resourceToPlace.getType())) {
            return resourceToPlace.getQnt() <= (space - storedResource.get().getQnt());
        }
        else //then I need to check that there is a free floor with enough space
            return storedResource.isEmpty() && resourceToPlace.getQnt() <= space;
    }


}

