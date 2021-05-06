package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class StartGame extends Message {

    public StartGame() {
        super("server", "client", Content.START_GAME);
    }



}
