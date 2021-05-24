package it.polimi.ingsw.network;

import java.io.Serializable;

public class Message implements Serializable, Comparable<Message> {

    private String receiverNickname;
    private String senderUser;
    private Content messageType;

    public void setSenderUser(String senderUser) {
        this.senderUser = senderUser;
    }

    public Message(String senderUser, String receiverNickname, Content messageType) {
        this.senderUser = senderUser;
        this.receiverNickname = receiverNickname;
        this.messageType = messageType;
    }

    public String getReceiverNickname() {
        return receiverNickname;
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

    @Override
    public int compareTo(Message o) {
        return 0;
    }
}
