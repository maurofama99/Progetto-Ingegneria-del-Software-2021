package it.polimi.ingsw.network.messagessc.Market;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class SendResource implements Answer {
    private final String message;

    public SendResource(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
