package it.polimi.ingsw.network.messagessc;

public class LoginValid implements Answer{
    private final String message;

    public LoginValid(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
