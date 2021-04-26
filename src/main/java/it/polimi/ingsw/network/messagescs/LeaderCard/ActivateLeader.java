package it.polimi.ingsw.network.messagescs.LeaderCard;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class ActivateLeader extends Message {

    public ActivateLeader(String senderUser, Content messageType) {
        super(senderUser, messageType);
    }
}
