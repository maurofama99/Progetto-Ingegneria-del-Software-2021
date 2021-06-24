package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * Message of end game in a normal (2 or more players) game. Starts the last turns
 */
public class EndGame extends Message {
    int playerNumber;

    /**
     * Default constructor
     * @param playerNumber how many players are in the game
     */
    public EndGame(int playerNumber) {
        super("player", "server", Content.END_GAME);
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
