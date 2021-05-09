package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class PlayerGenericMessage extends Message {
    private String genericMessage;

    public PlayerGenericMessage(String receiverNickname, String genericMessage) {
        super("server", receiverNickname, Content.GENERIC_MESSAGE);
        this.genericMessage = genericMessage;
    }

    @Override
    public String toString() {
        return genericMessage;
    }
}
