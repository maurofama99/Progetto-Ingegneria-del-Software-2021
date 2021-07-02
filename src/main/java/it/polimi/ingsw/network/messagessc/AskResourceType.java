package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * Ask a type of resource to the player for numerous actions.
 */
public class AskResourceType extends Message {


    public AskResourceType() {
        super("server", "client", Content.ASKTYPERESOURCE);
    }


}
