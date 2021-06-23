package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * This message is for activating a simple production provided by the dev cards or the basic production of the
 * personal board. We use 4 integers to see which production the player is trying to activate.
 */
public class ActivateProduction extends Message {
    int basic, slot1, slot2, slot3;

    public ActivateProduction(int basic, int slot1, int slot2, int slot3) {
        super( "client", Content.ACTIVATE_PRODUCTION);
        this.basic = basic;
        this.slot1 = slot1;
        this.slot2 = slot2;
        this.slot3 = slot3;
    }

    public int getBasic() {
        return basic;
    }

    public int getSlot1() {
        return slot1;
    }

    public int getSlot2() {
        return slot2;
    }

    public int getSlot3() {
        return slot3;
    }
}
