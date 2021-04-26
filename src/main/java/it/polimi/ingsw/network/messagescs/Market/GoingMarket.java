package it.polimi.ingsw.network.messagescs.Market;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class GoingMarket extends Message {

    public GoingMarket(String senderUser, Content messageType) {
        super(senderUser, messageType);
    }
}
