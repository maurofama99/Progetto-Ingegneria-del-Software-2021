package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * This message is sent with the nickname and the number of players we want to play with
 */
public class LoginData extends Message{
    private final String nickname;
    private final int numPlayers;

    /**
     * Default constructor
     * @param nickname nickname chosen by the player
     * @param numPlayers number of players he wants to play with
     */
    public LoginData(String nickname, int numPlayers) {
        super(nickname, "server", Content.LOGIN_DATA);
        this.nickname = nickname;
        this.numPlayers = numPlayers;
    }

    public String getNickname() {
        return nickname;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
}
