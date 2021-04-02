package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.player.warehouse.Warehouse;

import java.util.Arrays;

public class PersonalBoard {
    private final Warehouse warehouse;
    private Slot[] slots = new Slot[3];

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
}