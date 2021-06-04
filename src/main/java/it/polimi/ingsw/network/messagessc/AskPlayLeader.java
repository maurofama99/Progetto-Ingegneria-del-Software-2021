package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

import java.util.ArrayList;

public class AskPlayLeader extends Message {
    private ArrayList<LeaderCard> leaderCardsNotActivated;
    private boolean isEndTurn;

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
