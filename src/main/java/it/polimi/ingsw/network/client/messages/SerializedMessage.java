package it.polimi.ingsw.network.client.messages;

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
