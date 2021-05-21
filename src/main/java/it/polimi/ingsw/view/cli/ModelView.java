package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.warehouse.SerializableWarehouse;
import it.polimi.ingsw.model.player.warehouse.Warehouse;
import it.polimi.ingsw.model.resources.MarketTray;

import java.util.ArrayList;

public class ModelView {

    private Cli cli;

    private MarketTray marketTray;
    private DevCard[][] showedDeck;
    private Slot[] slots;
    private FaithTrack faithTrack;
    private SerializableWarehouse warehouse;
    private ArrayList<LeaderCard> leaderCards;


    public ModelView(Cli cli) {
        this.cli = cli;
    }


    public MarketTray getMarketTray() {
        return marketTray;
    }

    public SerializableWarehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(SerializableWarehouse warehouse) {
        this.warehouse = warehouse;
    }

    public DevCard[][] getShowedDeck() {
        return showedDeck;
    }

    public Slot[] getSlots() {
        return slots;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public void setMarketTray(MarketTray marketTray) {
        this.marketTray = marketTray;
    }

    public void setShowedDeck(DevCard[][] showedDeck) {
        this.showedDeck = showedDeck;
    }

    public void setSlots(Slot[] slots) {
        this.slots = slots;
    }

    public void setFaithTrack(FaithTrack faithTrack) {
        this.faithTrack = faithTrack;
    }

    public void setLeaderCards(ArrayList<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
    }
}
