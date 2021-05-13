package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

import java.util.ArrayList;

public class AskPlayLeader extends Message {
    private ArrayList<LeaderCard> leaderCardsNotActivated;

    public AskPlayLeader(ArrayList<LeaderCard> leaderCardsNotActivated) {
        super("server", "client", Content.ASK_PLAYLEADER);
        this.leaderCardsNotActivated = leaderCardsNotActivated;
    }

    public ArrayList<LeaderCard> getLeaderCardsNotActivated() {
        return leaderCardsNotActivated;
    }

    @Override
    public String toString() {
        return "You have these leader cards that are not yet activated!\n" + leaderCardsNotActivated;
    }
}
