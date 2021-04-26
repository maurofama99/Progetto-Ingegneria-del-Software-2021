package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Message;

import java.io.Serializable;

public class SerializedMessage implements Serializable{
    private final Message message;

    public SerializedMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
