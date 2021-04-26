package it.polimi.ingsw.network.messagessc;

public class PositiveResponse implements Answer{
    public final String message;

    public PositiveResponse(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
