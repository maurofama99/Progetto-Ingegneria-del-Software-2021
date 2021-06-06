package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * Displays the market to let the player know how the marbles are organized
 */
public class DisplayMarket extends Message {

    private MarketTray marketTray;

    public DisplayMarket(MarketTray marketTray) {
        super("server", "client", Content.DISPLAY_MARKET);
        this.marketTray = marketTray;
    }

    public MarketTray getMarketTray() {
        return marketTray;
    }

    @Override
    public String toString() {
        return marketTray.toString();
    }
}
