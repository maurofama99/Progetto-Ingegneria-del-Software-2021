package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.singleplayer.Token;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class TurnFavorTiles extends Message {

    public TurnFavorTiles(String senderUser) {
        super(senderUser, "client", Content.TURN_FAVORTILE);
    }

}
