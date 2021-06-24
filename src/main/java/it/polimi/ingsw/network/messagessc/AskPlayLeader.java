package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

import java.util.ArrayList;

/**
 * We let the player know what leader cards he hasn't activated yet
 */
public class AskPlayLeader extends Message {
    private ArrayList<LeaderCard> leaderCardsNotActivated;
    private boolean isEndTurn;

    /**
     * Default constructor
     * @param leaderCardsNotActivated the leader cards owned not activated
     * @param isEndTurn boolean to know if it is the start of the end of the turn
     */
    public AskPlayLeader(ArrayList<LeaderCard> leaderCardsNotActivated, boolean isEndTurn ) {
        super("server", "client", Content.ASK_PLAYLEADER);
        this.leaderCardsNotActivated = leaderCardsNotActivated;
        this.isEndTurn = isEndTurn;
    }

    public ArrayList<LeaderCard> getLeaderCardsNotActivated() {
        return leaderCardsNotActivated;
    }

    public boolean isEndTurn() {
        return isEndTurn;
    }

    @Override
    public String toString() {
        return "You have these leader cards that are not yet activated!\n" + leaderCardsNotActivated;
    }

}
