package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.FaithTrack;
import it.polimi.ingsw.model.player.faithtrack.PopeSpace;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.warehouse.Warehouse;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.resources.ResourceType;

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

    void fetchResourceType() throws IOException;

    void fetchResourcePlacement() throws IOException;

    void fetchSwapWhite(ResourceType type1, ResourceType type2) throws IOException;

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

    void displaySlots(Slot[] slots) throws IOException;

    void displayFaithTrack(FaithTrack faithTrack) throws IOException;

    void displayWarehouse(Warehouse warehouse) throws IOException;

    /**
     * Shows the market to the player to let him choose the line to buy
     * @param marketTray the current market tray
     */
    void displayMarket(MarketTray marketTray);

    /**
     * Displays the disconnected player
     * @param nicknameWhoDisconnected
     * @param text
     */
    void displayDisconnectedMsg(String nicknameWhoDisconnected, String text);

    /**
     * Displays an error message
     * @param errorMsg error displayed
     */
    void displayErrorMsg(String errorMsg);

    /**
     * Displays the personal board of the player, including slots, warehouses, everything that is
     * present on the personal board
     * @param personalBoard the player personal board
     */
    void displayPersonalBoard(PersonalBoard personalBoard);


    /**
     * Displays that a player reached a pope space and positions will be checked
     * @param popeSpace the pope space reached
     */
    void displayPopeSpaceActivation(PopeSpace popeSpace);




    /**
     * Ending message when a player wins
     * @param win message displayed
     */
    void displayWinningMsg(String win);

}
