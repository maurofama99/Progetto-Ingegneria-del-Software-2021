package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class DevCardBought extends Message {

    private DevCard DevCardBought;

    public DevCardBought(String senderUser, Content messageType) {
        super("server", "client", messageType);
    }
}
