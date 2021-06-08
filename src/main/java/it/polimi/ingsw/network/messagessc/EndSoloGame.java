package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * End message for the single player game
 */
public class EndSoloGame extends Message {

    boolean isPlayerWinner;

    public EndSoloGame(boolean isPlayerWinner) {
        super("model", "controller", Content.END_SOLOGAME);
        this.isPlayerWinner = isPlayerWinner;
    }

    public boolean isPlayerWinner() {
        return isPlayerWinner;
    }
}