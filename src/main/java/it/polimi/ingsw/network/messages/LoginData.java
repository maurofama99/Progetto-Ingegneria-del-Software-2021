package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.Contents;

/**
 * After the response for the login request, we send how many players we want to play with and our nickname
 */
public class LoginData extends Message{

    private final String nickname;
    private final int numPlayers;

    public LoginData(String nickname, int numPlayers){
        super(null, nickname, Contents.LOGIN_DATA);
        this.nickname=nickname;
        this.numPlayers=numPlayers;
    }

}
