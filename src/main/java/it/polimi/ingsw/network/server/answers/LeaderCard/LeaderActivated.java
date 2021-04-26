package it.polimi.ingsw.network.server.answers.LeaderCard;

import it.polimi.ingsw.network.server.answers.Answer;

public class LeaderActivated implements Answer {
    private final String message;

    public LeaderActivated(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
