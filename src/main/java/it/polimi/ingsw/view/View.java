package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.faithtrack.PopeSpace;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.warehouse.SerializableWarehouse;
import it.polimi.ingsw.model.player.warehouse.Warehouse;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.model.singleplayer.Token;
import javafx.event.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic view that needs to be implemented by each view type
 */
public interface View {

    /**
     * Fetches nickname of player
     */
    void fetchNickname() throws IOException;

    void localFetchNickname(Event event);

    void fetchResourceType() throws IOException;

    void fetchResourcePlacement(Resource resource) throws IOException;

    void fetchSwapWhite(ResourceType type1, ResourceType type2) throws IOException;

    void fetchExtraProd(Resource resource) throws IOException;

    void fetchPlayerAction(String message) throws IOException;

    void fetchDoneAction(String message,ArrayList<LeaderCard> leaderCards) throws IOException;

    void fetchPlayLeader(ArrayList<LeaderCard> leaderCards, boolean isEndTurn) throws IOException;

    /**
     * Displays a generic message for the view
     * @param genericMessage the message displayed
     */
    void displayGenericMessage(String genericMessage) throws IOException;

    void displayLeaderCards(ArrayList<LeaderCard> leaderCards) throws IOException;

    void displayDevCards(DevCard[][] devCards) throws IOException;

    void displayPersonalBoard(FaithTrack faithTrack, Slot[] slots, SerializableWarehouse warehouse);

    void updateOtherPersonalBoard(String name, FaithTrack fT, Slot[] slots, SerializableWarehouse wH, ArrayList<LeaderCard> lC);

    void displayGUIPersonalBoard(FaithTrack faithTrack, Slot[] slots, SerializableWarehouse warehouse) throws IOException;

    /**
     * Shows the market to the player to let him choose the line to buy
     * @param marketTray the current market tray
     */
    void displayMarket(MarketTray marketTray);

    void displayPopup(String message);

    /**
     * Displays the disconnected player
     * @param nicknameWhoDisconnected
     * @param text
     */
    void displayDisconnectedMsg(String nicknameWhoDisconnected, String text);

    /**
     * Ending message when a player wins
     * @param win message displayed
     */
    void displayWinningMsg();

    void displayToken(Token token);

    void forcedEnd(String nickname);

    void displayBasicProdPopup(int arrow, String message);
}
