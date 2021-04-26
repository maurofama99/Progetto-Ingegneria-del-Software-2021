package it.polimi.ingsw.network.messagessc.LeaderCard;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class LeaderActivated extends Message{

    public LeaderActivated(String senderUser, Content messageType) {
        super(senderUser, messageType);
    }

}
