package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class AskResourceType extends Message {


    public AskResourceType() {
        super("server", Content.ASKTYPERESOURCE);
    }


}
