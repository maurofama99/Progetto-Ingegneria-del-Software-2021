package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * If an action does not met the requirements in resources, this message is sent after the check
 */
public class NoAvailableResources extends Message {
    public NoAvailableResources(String receiverNickname) {
        super("server", receiverNickname, Content.NORESOURCE_AVAILABLE);
    }
}
