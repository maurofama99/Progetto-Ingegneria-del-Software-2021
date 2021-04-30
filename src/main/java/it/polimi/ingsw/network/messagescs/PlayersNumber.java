package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class PlayersNumber extends Message {

    private String nickName;
    int num;


    public PlayersNumber(String nickName, int num) {
        super(nickName, Content.PLAYERS_NUMBER);
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
