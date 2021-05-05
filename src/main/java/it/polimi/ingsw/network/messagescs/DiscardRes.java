package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class DiscardRes extends Message {

    public DiscardRes(String senderUser, Content messageType) {
        super(senderUser,"server", messageType);
    }
}
