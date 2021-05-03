package it.polimi.ingsw.network;

import java.io.Serializable;

public class Message implements Serializable {
    private String senderUser;
    private Content messageType;

    public void setSenderUser(String senderUser) {
        this.senderUser = senderUser;
    }

    public Message(String senderUser, Content messageType) {
        this.senderUser = senderUser;
        this.messageType = messageType;
    }

    public Message(Content messageType) {
        this.messageType = messageType;
    }

    public String getSenderUser() {
        return senderUser;
    }

    public Content getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return "Message[" +
                "senderUser='" + senderUser + '\'' +
                ", content=" + messageType +
                ']';
    }

}
