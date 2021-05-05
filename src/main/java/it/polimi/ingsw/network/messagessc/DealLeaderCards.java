package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

import java.util.ArrayList;

public class DealLeaderCards extends Message {

    private ArrayList<LeaderCard> leaderCards;

    public DealLeaderCards(ArrayList<LeaderCard> leaderCards, String receiverNickname) {
        super("server", receiverNickname, Content.DEAL_LEADERCARDS);
        this.leaderCards = leaderCards;
    }


    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    @Override
    public String toString() {

        int i=0;
        String text = " ";
        while (i<4) {
            text = leaderCards.get(i).toString() + "      ";
            i++;
        }


        return text;
    }
}
