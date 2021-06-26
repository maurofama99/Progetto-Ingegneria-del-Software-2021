package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.warehouse.SerializableWarehouse;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

import java.util.ArrayList;

/**
 * Displays the personal board of other players both in CLI or GUI
 */
public class SendOtherPersonalBoard extends Message {
    String name;
    FaithTrack faithTrack;
    Slot[] slot;
    SerializableWarehouse serializableWarehouse;
    ArrayList<LeaderCard> leaderCards;

    /**
     *
     * @param name player that owns the leader borad
     * @param faithTrack player's faithtrack (with cross updated)
     * @param slot player's slots (with devcards)
     * @param serializableWarehouse player's warehouses (with resources)
     * @param leaderCards player's activated leader cards
     */
    public SendOtherPersonalBoard(String name, FaithTrack faithTrack, Slot[] slot, SerializableWarehouse serializableWarehouse, ArrayList<LeaderCard> leaderCards) {
        super("server", "client", Content.SEND_PERSONALBOARD);
        this.name = name;
        this.faithTrack = faithTrack;
        this.slot = slot;
        this.serializableWarehouse = serializableWarehouse;
        this.leaderCards = leaderCards;
    }

    public String getName() {
        return name;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public Slot[] getSlot() {
        return slot;
    }

    public SerializableWarehouse getSerializableWarehouse() {
        return serializableWarehouse;
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }
}
