package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class ResourcePlacement extends Message {
    String floor;

    public ResourcePlacement(String senderUser, String floor) {
        super(senderUser, "server", Content.RESOURCE_PLACEMENT);
        this.floor=floor;
    }

    public String getFloor() {
        return floor;
    }
}
