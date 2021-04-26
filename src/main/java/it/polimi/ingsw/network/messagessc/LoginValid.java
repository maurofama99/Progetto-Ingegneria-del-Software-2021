package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class LoginValid extends Message {
    private final String message;

    public LoginValid(String message) {
        super("nickname/ip", Content.LOGIN_SUCCESSFUL);
        this.message = message;
    }


    public String getMessage() {
        return message;
    }
}
