package it.polimi.ingsw.network.messagessc;

public class LoginRequest implements Answer {
    private final String message;

    public LoginRequest(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
