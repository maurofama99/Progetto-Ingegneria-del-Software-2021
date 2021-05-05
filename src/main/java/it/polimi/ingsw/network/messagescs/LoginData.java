package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class LoginData extends Message{
    private final String nickname;
    private final int numPlayers;

    public LoginData(String nickname, int numPlayers) {
        super(nickname, Content.LOGIN_DATA);
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
