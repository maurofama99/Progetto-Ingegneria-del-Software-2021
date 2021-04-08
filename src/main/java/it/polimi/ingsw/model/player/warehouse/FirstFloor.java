package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;

public class FirstFloor extends Floor {


    public FirstFloor() {
        super(1);
    }

    @Override
    public boolean differentResources(Resource resourceToPlace) {
        SecondFloor secondFloor = new SecondFloor();
        ThirdFloor thirdFloor = new ThirdFloor();

        if (thirdFloor.getStoredResource().isPresent() && thirdFloor.getStoredResource().get().getType().equals(resourceToPlace.getType()))
                return false;

        return secondFloor.getStoredResource().isEmpty() || !secondFloor.getStoredResource().get().getType().equals(resourceToPlace.getType());

    }
}
