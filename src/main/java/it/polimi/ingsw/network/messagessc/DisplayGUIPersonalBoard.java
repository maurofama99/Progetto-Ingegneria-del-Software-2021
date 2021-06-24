package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.warehouse.SerializableWarehouse;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * Displays the GUI personal board keeping the slots, track and warehouse updated
 */
public class DisplayGUIPersonalBoard extends Message {
    private FaithTrack faithTrack;
    private Slot[] slots;
    private SerializableWarehouse serializableWarehouse;

    /**
     * Default constructor
     * @param faithTrack player's faithtrack (with cross updated)
     * @param slots player's slots (with devcards)
     * @param serializableWarehouse playerìs warehouses (with resources)
     */
    public DisplayGUIPersonalBoard(FaithTrack faithTrack, Slot[] slots, SerializableWarehouse serializableWarehouse) {
        super("server", "client", Content.DISPLAY_GUI_PERSONALBOARD);
        this.faithTrack = faithTrack;
        this.slots = slots;
        this.serializableWarehouse = serializableWarehouse;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public Slot[] getSlots() {
        return slots;
    }

    public SerializableWarehouse getSerializableWarehouse() {
        return serializableWarehouse;
    }
}
