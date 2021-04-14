package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.warehouse.Warehouse;
import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;
import java.util.Arrays;

public class PersonalBoard {
    private final Warehouse warehouse;
    private Slot[] slots = new Slot[3];
    private FaithTrack faithTrack;
    private Player player;

    public Player getPlayer() { return player; }

    public void setFaithTrack(FaithTrack faithTrack) {
        this.faithTrack = faithTrack;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public PersonalBoard(Warehouse warehouse) {
        this.warehouse = warehouse;
        slots[0] = new Slot(SlotNumber.FIRST);
        slots[1] = new Slot(SlotNumber.SECOND);
        slots[2] = new Slot(SlotNumber.THIRD);
    }

    public Slot[] getSlots() {
        return slots;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    /**
     * Method that swaps two resources chosen by the player to one single resource.
     * @param firstInput one resource used to product the resource in output.
     * @param secondInput one resource used to product the resource in output.
     * @param Output one resource that the player gets by the production.
     */
    public void basicProduction(Resource firstInput, Resource secondInput, Resource Output){
        ArrayList<Resource> resourceToRemove = new ArrayList<>();
        resourceToRemove.add(firstInput);
        resourceToRemove.add(secondInput);

        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        resourcesToAdd.add(Output);

        getWarehouse().removeResources(resourceToRemove);
        getWarehouse().getStrongBox().addResourceToStrongBox(resourcesToAdd);
    }

}
