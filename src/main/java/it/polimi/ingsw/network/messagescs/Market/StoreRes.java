package it.polimi.ingsw.network.messagescs.Market;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class StoreRes extends Message {

    public StoreRes(String senderUser, Content messageType) {
        super(senderUser, messageType);
    }
}
