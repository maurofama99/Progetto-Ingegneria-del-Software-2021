package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class NoAvailableResources extends Message {
    public NoAvailableResources(String receiverNickname) {
        super("server", receiverNickname, Content.NORESOURCE_AVAILABLE);
    }
}
