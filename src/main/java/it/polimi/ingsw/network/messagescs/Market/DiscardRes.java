package it.polimi.ingsw.network.messagescs.Market;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class DiscardRes extends Message {

    public DiscardRes(String senderUser, Content messageType) {
        super(senderUser, messageType);
    }
}
