package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class Disconnect extends Message {


    public Disconnect(String senderUser, Content messageType) {
        super(senderUser, messageType);
    }
}