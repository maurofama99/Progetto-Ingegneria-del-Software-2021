package it.polimi.ingsw.network.messagescs.Market;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class SelectLine extends Message {

    public SelectLine(String senderUser, Content messageType) {
        super(senderUser, messageType);
    }
}
