package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;

public abstract class Floor {
    protected Resource storedResource;
    private int space;

    public Floor(Resource storedResource, int space) {
        this.storedResource = storedResource;
        this.space = space;
    }

    public Floor(Resource storedResource) {
        this.storedResource = storedResource;
    }

    public Resource getStoredResource() {
        return storedResource;
    }

    public int getSpace() {
        return space;
    }

    public void setStoredResource(Resource storedResource) {
        this.storedResource = storedResource;
    }


    //checkAvailableSpace checks if there is space in the different floors for the extra resource that the player needs to place
    public boolean checkCorrectPlacement(Resource resourceToPlace) {
        //se c'è abbastanza spazio sul piano se la risorsa c'è gia
        if (storedResource.getType() == resourceToPlace.getType()) {
            return resourceToPlace.getQnt() <= (space - storedResource.getQnt());
        }
        else //then I need to check that there is a free floor with enough space
            return storedResource == null && resourceToPlace.getQnt() <= space;
    }


}

