package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.singleplayer.Token;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * Lets the client know a player reached a pope space and to check if they should turn or discard the tile
 */
public class TurnFavorTiles extends Message {
    /**
     * Default constructor
     * @param senderUser server
     */
    public TurnFavorTiles(String senderUser) {
        super(senderUser, "client", Content.TURN_FAVORTILE);
    }

}
