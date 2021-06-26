package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * Message for placing a resource in the deposit, switching it or using an extra depot
 */
public class ResourcePlacement extends Message {
    String floor;
    int sourceFloor = 0;
    int destFloor = 0;
    String type;

    /**
     * Default constructor for placing a resource in a floor
     * @param senderUser nickname of the player
     * @param floor floor chosen by the player
     */
    public ResourcePlacement(String senderUser, String floor) {
        super(senderUser, "server", Content.RESOURCE_PLACEMENT);
        this.floor = floor;
    }

    /**
     * Default constructor for the switch action
     * @param senderUser nickname of the player
     * @param floor floor chosen
     * @param sourceFloor source of the resource(s) to be switched
     * @param destFloor destination of the resource(s) to be switched
     */
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
