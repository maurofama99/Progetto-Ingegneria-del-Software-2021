package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class ResourcePlacement extends Message {
    int floor;

    public ResourcePlacement(String senderUser, int floor) {
        super(senderUser, Content.RESOURCE_PLACEMENT);
        this.floor=floor;
    }

    public int getFloor() {
        return floor;
    }
}
