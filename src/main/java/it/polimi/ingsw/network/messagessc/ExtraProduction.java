package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.resources.Resource;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class ExtraProduction extends Message {
    Resource resource;

    public ExtraProduction(Resource resource) {
        super("server", "client", Content.EXTRA_PRODUCTION);
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }
}