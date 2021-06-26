package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * Message used to discard a leader card to get one faith point
 */
public class DiscardOneLeader extends Message {
    private int leaderCard;
    private boolean isEndTurn;

    /**
     * Default constructor
     * @param leaderCard the leader card to discard
     * @param isEndTurn we use a boolean to know when the action is performed (start or end turn)
     */
    public DiscardOneLeader(int leaderCard, boolean isEndTurn) {
        super( "server", Content.DISCARDED_LEADER);
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
