package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * A login request sent by the server to the client
 */
public class LoginRequest extends Message {
    /**
     * Default constructor
     */
    public LoginRequest() {
        super("server", "client", Content.LOGIN_REQUEST);
    }

    @Override
    public String toString(){
        return "Login request[" + "senderUser=" + getSenderUser() +
                ", type =" + getMessageType() +
                "]";
    }
}
