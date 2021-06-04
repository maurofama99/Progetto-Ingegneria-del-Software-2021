package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class ActivateLeader extends Message {

    private int leaderCard;
    boolean isEndTurn;

    public ActivateLeader(int leaderCard, boolean isEndTurn) {
        super("client", "server", Content.ACTIVATE_LEADER);
        this.leaderCard = leaderCard;
        this.isEndTurn=isEndTurn;
    }

    public int getLeaderCard() {
        return leaderCard;
    }

    public boolean isEndTurn() {
        return isEndTurn;
    }
}
