package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class LoginRequest extends Message {
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
