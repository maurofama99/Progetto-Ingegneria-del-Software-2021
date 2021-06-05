package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Serializable class that acts as the depot of the player. Resources are put into arraylist, that can be modified by actions in the game
 *
 */
public class SerializableWarehouse implements Serializable {
    private final ArrayList<Resource> floors;
    private final ArrayList<Resource> extraFloors;
    private final Resource[] strongbox;

    public SerializableWarehouse(Warehouse warehouse) {
        this.floors = warehouse.getDepot().serializableFloorStamp(warehouse.getDepot().getFloors());
        this.extraFloors = warehouse.getDepot().serializableFloorStamp(warehouse.getDepot().getExtraFloors());
        this.strongbox = warehouse.getStrongBox().getStoredResources();
    }

    public ArrayList<Resource> getFloors() {
        return floors;
    }

    public ArrayList<Resource> getExtraFloors() {
        return extraFloors;
    }

    public Resource[] getStrongbox() {
        return strongbox;
    }

}
