package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class SelectLine extends Message {

    public SelectLine(String senderUser, Content messageType) {
        super(senderUser,"server", messageType);
    }
}
