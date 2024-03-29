package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.warehouse.SerializableWarehouse;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

import java.util.ArrayList;

/**
 * Displays the personal board and also let the player know which leader cards are active in the CLI
 */
public class DisplayPersonalBoard extends Message {
    private FaithTrack faithTrack;
    private Slot[] slots;
    private SerializableWarehouse serializableWarehouse;
    private ArrayList<LeaderCard> activeLeaderCards;

    /**
     * Default constructor
     * @param faithTrack player's faithtrack (with cross updated)
     * @param slots player's slots (with devcards)
     * @param serializableWarehouse player's warehouses (with resources)
     * @param activeLeaderCards player's activated leader cards
     */
    public DisplayPersonalBoard(FaithTrack faithTrack, Slot[] slots, SerializableWarehouse serializableWarehouse, ArrayList<LeaderCard> activeLeaderCards) {
        super("server", "client", Content.DISPLAY_PERSONALBOARD);
        this.faithTrack = faithTrack;
        this.slots = slots;
        this.serializableWarehouse = serializableWarehouse;
        this.activeLeaderCards = activeLeaderCards;
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

    public ArrayList<LeaderCard> getActiveLeaderCards() {
        return activeLeaderCards;
    }
}
