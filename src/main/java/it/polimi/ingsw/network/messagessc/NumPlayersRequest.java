package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class NumPlayersRequest extends Message {
    public NumPlayersRequest() {
        super("server", Content.NUM_PLAYERS_REQUEST);
    }

}
