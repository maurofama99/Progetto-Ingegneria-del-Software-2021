package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * This message is a discard action for the leader cards at the start of the match
 * We use two int to know which leader cards wants to discard
 */
public class DiscardLeader extends Message {

    private int leaderCard1;
    private int leaderCard2;

    /**
     * Default constructor
     * @param senderUser the nickname of the player
     * @param leaderCard1 first leader card to discard
     * @param leaderCard2 second leader card to discard
     */
    public DiscardLeader(String senderUser, int leaderCard1, int leaderCard2) {
        super(senderUser,"server", Content.DISCARD_LEADER);
        this.leaderCard1 = leaderCard1;
        this.leaderCard2 = leaderCard2;
    }

    public int getLeaderCard1() {
        return leaderCard1;
    }

    public int getLeaderCard2() {
        return leaderCard2;
    }
}
