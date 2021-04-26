package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.Contents;

public class GenericMessage extends Message{

    private String text;

    public GenericMessage(String token, String senderUser, Contents messageContents) {
        super(senderUser, messageContents);
    }

    public String getText() {
        return text;
    }
}
