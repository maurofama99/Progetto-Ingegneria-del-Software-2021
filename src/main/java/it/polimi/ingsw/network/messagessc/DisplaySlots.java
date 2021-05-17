package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class DisplaySlots extends Message {
    private Slot[] slots;

    public DisplaySlots(Slot[] slots) {
        super("server", "client", Content.DISPLAY_SLOTS);
        this.slots = slots;
    }

    public Slot[] getSlots() {
        return slots;
    }
}
