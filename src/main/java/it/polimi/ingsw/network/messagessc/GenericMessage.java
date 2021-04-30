package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class GenericMessage extends Message {
    private String genericMessage;

    public GenericMessage(String genericMessage) {
        super("server", Content.GENERIC_MESSAGE);
        this.genericMessage = genericMessage;
    }

    @Override
    public String toString() {
        return genericMessage;
    }
}
