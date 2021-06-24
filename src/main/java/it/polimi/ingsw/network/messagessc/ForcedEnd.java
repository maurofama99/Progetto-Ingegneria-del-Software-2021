package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * If a player disconnects the game is forced to end instantly
 */
public class ForcedEnd extends Message {

    private final String nickname;

    /**
     * Default constructor
     * @param nickname nickname of the player who disconnected
     */
    public ForcedEnd(String nickname) {
        super("server", "client", Content.FORCEDEND);
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
