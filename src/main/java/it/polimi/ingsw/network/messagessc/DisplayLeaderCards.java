package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

import java.util.ArrayList;

public class DisplayLeaderCards extends Message {

    private ArrayList<LeaderCard> leaderCards;

    public DisplayLeaderCards(ArrayList<LeaderCard> leaderCards, String receiverNickname) {
        super("server", receiverNickname, Content.DISPLAY_LEADERCARDS);
        this.leaderCards = leaderCards;
    }


    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

}
