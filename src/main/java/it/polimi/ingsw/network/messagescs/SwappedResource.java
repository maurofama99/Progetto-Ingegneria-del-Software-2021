package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class SwappedResource extends Message {
    String resourceType;

    public SwappedResource(String senderUser, String resourceType) {
        super(senderUser, "server", Content.SWAPPED_RESOURCE);
        this.resourceType = resourceType;
    }

    public String getResourceType() {
        return resourceType;
    }
}
