package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class AskResourcePlacement extends Message {

    private final Resource resource;

    public AskResourcePlacement(Resource resource) {
        super("server", "client", Content.ASK_RESOURCE_PLACEMENT);
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }
}
