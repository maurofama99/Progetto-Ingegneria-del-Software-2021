package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

import java.util.ArrayList;

/**
 * Asks at the end of the turn if the player wants to pass or activate a leader card
 */
public class AskDone extends Message {
    String Question;
    ArrayList<LeaderCard> leaderCards;

    public AskDone(ArrayList<LeaderCard> leaderCards) {
        super("server", "client", Content.ASK_DONE);
        Question = "Type DONE if you are done or LEADER if you want to play a leader card.";
        this.leaderCards= leaderCards;
    }

    public String getQuestion() {
        return Question;
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }
}
