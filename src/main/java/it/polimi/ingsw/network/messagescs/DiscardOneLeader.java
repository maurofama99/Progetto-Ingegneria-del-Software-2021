package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class DiscardOneLeader extends Message {
    private int leaderCard;

    public DiscardOneLeader(int leaderCard) {
        super("client", "server", Content.DISCARDED_LEADER);
        this.leaderCard = leaderCard;
    }

    public int getLeaderCard() {
        return leaderCard;
    }
}
