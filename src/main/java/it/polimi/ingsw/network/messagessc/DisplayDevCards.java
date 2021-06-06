package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * Displays the leader cards in the shop at that moment
 */
public class DisplayDevCards extends Message {
    private DevCard[][] showedCards;

    public DisplayDevCards(DevCard[][] showedCards) {
        super("server", "client", Content.DISPLAY_DEVCARDS);
        this.showedCards = showedCards;
    }

    public DevCard[][] getShowedCards() {
        return showedCards;
    }
}
