package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

import java.util.ArrayList;

/**
 * Displays an arraylist of four leader cards when they are dealt at the start of the match
 */
public class DisplayLeaderCards extends Message {

    private ArrayList<LeaderCard> leaderCards;

    /**
     * Default constructor
     * @param leaderCards arraylist of leader cards dealt
     * @param receiverNickname nickname of the player that received them
     */
    public DisplayLeaderCards(ArrayList<LeaderCard> leaderCards, String receiverNickname) {
        super("server", receiverNickname, Content.DISPLAY_LEADERCARDS);
        this.leaderCards = leaderCards;
    }


    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

}
