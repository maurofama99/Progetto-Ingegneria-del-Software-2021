package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * This is a generic message from the server to the client, with a string that contains our message
 */
public class GenericMessage extends Message {
    private String genericMessage;

    public GenericMessage(String genericMessage) {
        super("server", "client", Content.GENERIC_MESSAGE);
        this.genericMessage = genericMessage;
    }

    @Override
    public String toString() {
        return genericMessage;
    }
}
