package it.polimi.ingsw.network.messagessc.Market;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class SendResource extends Message {

    public SendResource(String senderUser, Content messageType) {
        super(senderUser, messageType);
    }
}
