package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.singleplayer.Token;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * A token is played in the single plauer
 */
public class TurnToken extends Message {
    private Token token;

    /**
     * Default constructor
     * @param token the token that was turned
     */
    public TurnToken(Token token) {
        super("server", "client", Content.TURN_TOKEN);
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}
