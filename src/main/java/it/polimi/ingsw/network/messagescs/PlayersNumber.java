package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class PlayersNumber extends Message {
    int num;
    public PlayersNumber(int num) {
        super("server", Content.PLAYERS_NUMBER);
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
