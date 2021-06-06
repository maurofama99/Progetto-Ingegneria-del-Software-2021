package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.singleplayer.Token;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * After a cross reaches a pope tile, the game does a check and turns or remove the tiles of all players based on where they are
 */
public class TurnFavorTiles extends Message {

    public TurnFavorTiles(String senderUser) {
        super(senderUser, "client", Content.TURN_FAVORTILE);
    }

}
