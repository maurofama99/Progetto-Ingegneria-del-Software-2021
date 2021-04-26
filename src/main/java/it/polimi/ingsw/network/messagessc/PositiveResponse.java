package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class PositiveResponse extends Message {
    public final String message;

    public PositiveResponse(String message) {
        super("nickname/ip", Content.POSITIVE);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
