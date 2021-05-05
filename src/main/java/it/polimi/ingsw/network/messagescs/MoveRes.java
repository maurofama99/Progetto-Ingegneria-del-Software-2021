package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class MoveRes extends Message {

    public MoveRes(String senderUser, Content messageType) {
        super(senderUser, messageType);
    }
}
