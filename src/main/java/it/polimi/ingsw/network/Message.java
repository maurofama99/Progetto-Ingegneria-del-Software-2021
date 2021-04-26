package it.polimi.ingsw.network;

import it.polimi.ingsw.network.Content;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private final String senderUser;
    private final Content messageType;

    public Message(String senderUser, Content messageType) {
        this.senderUser = senderUser;
        this.messageType = messageType;
    }


    public String getSenderUser(){
        return senderUser;
    }

    public Content getMessageType() {
        return messageType;
    }

    @Override
    public String toString(){
        return "Message[" +
                "senderUser='" + senderUser + '\'' +
                ", content=" + messageType +
                ']';
    }

}
