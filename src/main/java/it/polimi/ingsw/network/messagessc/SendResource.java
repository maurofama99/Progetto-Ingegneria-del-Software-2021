package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class SendResource extends Message {

    public SendResource(Content messageType) {
        super("server", "client", messageType);
    }
}
