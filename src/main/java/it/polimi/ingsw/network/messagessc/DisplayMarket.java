package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class DisplayMarket extends Message {

    private MarketTray marketTray;

    public DisplayMarket(MarketTray marketTray) {
        super("server", "client", Content.DISPLAY_MARKET);
        this.marketTray = marketTray;
    }

    public MarketTray getMarketTray() {
        return marketTray;
    }
}
