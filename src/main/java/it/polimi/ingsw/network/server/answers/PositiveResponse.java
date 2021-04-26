package it.polimi.ingsw.network.server.answers;

public class PositiveResponse implements Answer{
    public final String message;

    public PositiveResponse(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
