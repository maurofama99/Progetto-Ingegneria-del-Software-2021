package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * This message is sent by the player to activate a leader card. We check the requirements
 * and if met we proceed to let the card be used
 */
public class ActivateLeader extends Message {

    private int leaderCard;
    boolean isEndTurn;

    public ActivateLeader(int leaderCard, boolean isEndTurn) {
        super("server", Content.ACTIVATE_LEADER);
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
