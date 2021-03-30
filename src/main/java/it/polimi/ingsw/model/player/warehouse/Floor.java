package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;

import java.util.Optional;

public abstract class Floor {
    protected Optional<Resource> storedResource;
    private int space;

    public Floor(Resource storedResource, int space) {
        this.storedResource = Optional.ofNullable(storedResource);
        this.space = space;
    }

    public Floor(Resource storedResource) {
        this.storedResource = Optional.ofNullable(storedResource);
    }

    public Optional<Resource> getStoredResource() {
        return storedResource;
    }

    public int getSpace() {
        return space;
    }

    public void setStoredResource(Resource storedResource) {
        this.storedResource = Optional.ofNullable(storedResource);
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

