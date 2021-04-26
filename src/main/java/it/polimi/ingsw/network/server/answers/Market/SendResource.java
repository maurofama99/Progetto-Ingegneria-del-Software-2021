package it.polimi.ingsw.network.server.answers.Market;

import it.polimi.ingsw.network.server.answers.Answer;

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
