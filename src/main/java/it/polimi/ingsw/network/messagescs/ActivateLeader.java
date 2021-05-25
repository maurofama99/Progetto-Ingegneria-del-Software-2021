package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class ActivateLeader extends Message {

    private int leaderCard;

    public ActivateLeader(int leaderCard) {
        super("client", "server", Content.ACTIVATE_LEADER);
        this.leaderCard = leaderCard;
    }

    public int getLeaderCard() {
        return leaderCard;
    }
}
