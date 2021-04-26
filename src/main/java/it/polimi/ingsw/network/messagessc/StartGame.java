package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class StartGame extends Message {

    public StartGame(String senderUser, Content messageType) {
        super(senderUser, Content.START_GAME);
    }



}
