package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.warehouse.Depot;
import it.polimi.ingsw.model.player.warehouse.SerializableWarehouse;
import it.polimi.ingsw.model.player.warehouse.Warehouse;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.view.gui.Gui;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The ModelView class is the class that responds to the action of the players and that is updated
 * by their actions. It has getters and setters for every aspect of the game that changes, such as the market tray,
 * the slots, the dev card shop... also the personal boards of other players so you can access them to see
 * how they are doing. Getters are also useful in the GUI to match images since it keeps them updated.
 */
public class ModelView {

    private Cli cli;
    private Gui gui;

    private MarketTray marketTray;
    private DevCard[][] showedDeck;
    private Slot[] slots;
    private FaithTrack faithTrack;
    private SerializableWarehouse warehouse;
    private ArrayList<LeaderCard> leaderCards;
    private ArrayList<LeaderCard> activeLeaderCards = new ArrayList<>();
    private HashMap<String, PersonalBoard> othersPersonalBoards = new HashMap<>();


    public ModelView(Cli cli) {
        this.cli = cli;
    }

    public ModelView(Gui gui) {
        this.gui = gui;
    }

    public HashMap<String, PersonalBoard> getOthersPersonalBoards() {
        return othersPersonalBoards;
    }

    public MarketTray getMarketTray() {
        return marketTray;
    }

    public SerializableWarehouse getWarehouse() {
        return warehouse;
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public ArrayList<LeaderCard> getActiveLeaderCards() {
        return activeLeaderCards;
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

    public void setActiveLeaderCards(ArrayList<LeaderCard> activeLeaderCards) {
        this.activeLeaderCards = activeLeaderCards;
    }

    public void addOthersPB (String name, FaithTrack fT, Slot[] s, SerializableWarehouse wH, ArrayList<LeaderCard> lC){
        PersonalBoard pB = new PersonalBoard(wH, s, fT, lC);
        othersPersonalBoards.put(name, pB);
    }

    public void updateOthersPB(String name, FaithTrack fT, Slot[] s, SerializableWarehouse wH, ArrayList<LeaderCard> lC){
        if (othersPersonalBoards.isEmpty() || !othersPersonalBoards.containsKey(name)){
            addOthersPB(name, fT, s, wH, lC);
        }
        else {
            othersPersonalBoards.get(name).setFaithTrack(fT);
            othersPersonalBoards.get(name).setSlots(s);
            othersPersonalBoards.get(name).setSerializableWarehouse(wH);
            othersPersonalBoards.get(name).setActiveLeaderCards(lC);
        }
    }
}
