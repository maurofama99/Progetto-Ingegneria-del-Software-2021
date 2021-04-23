package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.Contents;

import java.io.Serializable;

public class Message implements Serializable {

    private final String senderUser;
    private final Contents messageType;

    public Message(String senderUser, Contents messageType) {
        this.senderUser = senderUser;
        this.messageType = messageType;
    }


    public String getSenderUser(){
        return senderUser;
    }

    public Contents getMessageType() {
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
