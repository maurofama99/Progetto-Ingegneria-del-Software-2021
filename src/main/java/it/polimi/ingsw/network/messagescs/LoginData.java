package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class LoginData extends Message{
    private final String nickname;

    public LoginData (String nickname) {
        super(nickname, Content.LOGIN_DATA);
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
