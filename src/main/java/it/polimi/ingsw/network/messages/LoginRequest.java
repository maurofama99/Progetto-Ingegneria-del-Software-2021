package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.Contents;

/**
 * Requests a connection to a waiting room
 */
public class LoginRequest extends Message{

    public LoginRequest(){
        super(null, null, Contents.LOGIN_REQUEST);
    }

    @Override
    public String toString(){
        return "Login request[" + "senderUser=" + getSenderUser() +
                ", content =" + getMessageContents() +
                "]";
    }
}
