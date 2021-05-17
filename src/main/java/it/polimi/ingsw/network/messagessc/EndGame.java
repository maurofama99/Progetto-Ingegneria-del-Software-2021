package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class EndGame extends Message {

    public EndGame() {
        super("player", "server", Content.END_GAME);
    }
}
