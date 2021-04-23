package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.Contents;

import java.io.Serializable;

public class Message implements Serializable {

    private final String token;
    private final String senderUser;
    private final Contents messageContents;

    public Message(String token, String senderUser, Contents messageContents) {
        this.token = token;
        this.senderUser = senderUser;
        this.messageContents = messageContents;
    }


    public String getSenderUser(){
        return senderUser;
    }

    public String getToken(){
        return token;
    }

    public Contents getMessageContents(){
        return messageContents;
    }

    public void processMessage(){

    }

    @Override
    public String toString(){
        return "Message[" +
                "senderUser='" + senderUser + '\'' +
                ", content=" + messageContents +
                ']';
    }
}
