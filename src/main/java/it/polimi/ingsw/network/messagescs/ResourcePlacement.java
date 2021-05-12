package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class ResourcePlacement extends Message {
    String floor;
    int sourceFloor = 0;
    int destFloor = 0;
    String type;

    public ResourcePlacement(String senderUser, String floor) {
        super(senderUser, "server", Content.RESOURCE_PLACEMENT);
        this.floor = floor;
    }

    public ResourcePlacement(String senderUser, String floor, int sourceFloor, int destFloor) {
        super(senderUser, "server", Content.RESOURCE_PLACEMENT);
        this.floor=floor;
        this.destFloor = destFloor;
        this.sourceFloor = sourceFloor;
    }

    public ResourcePlacement(String senderUser, String floor, String type) {
        super(senderUser, "server", Content.RESOURCE_PLACEMENT);
        this.floor = floor;
        this.type = type;
    }

    public String getFloor() {
        return floor;
    }

    public int getSourceFloor() {
        return sourceFloor;
    }

    public int getDestFloor() {
        return destFloor;
    }

    public String getType() {
        return type;
    }
}
