package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class ActivateLeader extends Message {

    private LeaderCard leaderCard;

    public ActivateLeader(String senderUser, LeaderCard leaderCard) {
        super(senderUser, "server", Content.ACTIVATE_LEADER);
        this.leaderCard = leaderCard;
    }

    public LeaderCard getLeaderCard() {
        return leaderCard;
    }
}
