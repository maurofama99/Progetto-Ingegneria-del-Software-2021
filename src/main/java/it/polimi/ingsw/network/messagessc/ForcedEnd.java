package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class ForcedEnd extends Message {

    private final String nickname;

    public ForcedEnd(String nickname) {
        super("server", "client", Content.FORCEDEND);
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
