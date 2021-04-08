package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;

public class SecondFloor extends Floor {

    public SecondFloor() {
        super( 2);
    }

    @Override
    public boolean differentResources(Resource resourceToPlace) {
        FirstFloor firstFloor = new FirstFloor();
        ThirdFloor thirdFloor = new ThirdFloor();

        if (firstFloor.getStoredResource().isPresent() && firstFloor.getStoredResource().get().getType().equals(resourceToPlace.getType()))
                return false;

        return thirdFloor.getStoredResource().isEmpty() || !thirdFloor.getStoredResource().get().getType().equals(resourceToPlace.getType());
    }

}
