package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;

public class ThirdFloor extends Floor {

    public ThirdFloor() {
        super(3);
    }

    @Override
    public boolean differentResources(Resource resourceToPlace) {
        SecondFloor secondFloor = new SecondFloor();
        FirstFloor firstFloor = new FirstFloor();

        if (secondFloor.getStoredResource().isPresent() && secondFloor.getStoredResource().get().getType().equals(resourceToPlace.getType()))
                return false;

        return firstFloor.getStoredResource().isEmpty() || !firstFloor.getStoredResource().get().getType().equals(resourceToPlace.getType());

    }
}
