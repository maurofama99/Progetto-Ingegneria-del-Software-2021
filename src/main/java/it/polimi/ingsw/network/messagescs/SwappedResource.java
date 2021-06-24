package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * Message for the swap white action when two swap whites effect are actives
 */
public class SwappedResource extends Message {
    String resourceType;

    /**
     * Default constructor
     * @param senderUser nickname of the player
     * @param resourceType resource type chosen
     */
    public SwappedResource(String senderUser, String resourceType) {
        super(senderUser, "server", Content.SWAPPED_RESOURCE);
        this.resourceType = resourceType;
    }

    public String getResourceType() {
        return resourceType;
    }
}
