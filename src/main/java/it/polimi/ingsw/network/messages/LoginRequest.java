package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.Contents;

/**
 * Requests a connection to a waiting room
 */
public class LoginRequest extends Message{

    public LoginRequest(String senderUser, Contents messageType) {
        super(senderUser, messageType);
    }

    @Override
    public String toString(){
        return "Login request[" + "senderUser=" + getSenderUser() +
                ", type =" + getMessageType() +
                "]";
    }
}

