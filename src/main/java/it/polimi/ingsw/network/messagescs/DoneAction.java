package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class DoneAction extends Message {

    public DoneAction(String senderUser, Content messageType) {
        super(senderUser, messageType);
    }
}
