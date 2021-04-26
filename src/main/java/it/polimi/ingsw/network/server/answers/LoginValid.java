package it.polimi.ingsw.network.server.answers;

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
