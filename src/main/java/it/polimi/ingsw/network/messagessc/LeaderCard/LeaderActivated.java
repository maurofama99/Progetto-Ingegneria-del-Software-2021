package it.polimi.ingsw.network.messagessc.LeaderCard;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

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
