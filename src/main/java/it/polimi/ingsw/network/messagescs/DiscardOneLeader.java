package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class DiscardOneLeader extends Message {
    private int leaderCard;
    private boolean isEndTurn;

    public DiscardOneLeader(int leaderCard, boolean isEndTurn) {
        super("client", "server", Content.DISCARDED_LEADER);
        this.leaderCard = leaderCard;
        this.isEndTurn = isEndTurn;
    }

    public int getLeaderCard() {
        return leaderCard;
    }

    public boolean isEndTurn() {
        return isEndTurn;
    }
}
