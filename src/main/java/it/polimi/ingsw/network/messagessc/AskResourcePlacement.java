package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * Asks where the player wants to put a received resource
 */
public class AskResourcePlacement extends Message {

    private final Resource resource;

    /**
     * Default constructor
     * @param resource the resource received
     */
    public AskResourcePlacement(Resource resource) {
        super("server", "client", Content.ASK_RESOURCE_PLACEMENT);
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }
}
