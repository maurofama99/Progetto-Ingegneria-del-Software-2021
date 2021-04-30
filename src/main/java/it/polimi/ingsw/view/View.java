package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Slot;
import it.polimi.ingsw.model.player.faithtrack.PopeSpace;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.resources.MarketTray;

import java.io.IOException;
import java.util.List;

/**
 * Generic view that needs to be implemented by each view type
 */
public interface View {

    /**
     * Fetches nickname of player
     */
    void fetchNickname() throws IOException;

    /**
     * Displays a generic message for the view
     * @param genericMessage the message displayed
     */
    void displayGenericMessage(String genericMessage) throws IOException;


    /**
     * Displays the result of an attempt of connection
     * @param nicknameIsOk nickname checked for the connection
     * @param connectionIsOk the connection was successful
     * @param nickname nickname checked
     */
    void displayLoginResult(boolean nicknameIsOk, boolean connectionIsOk, String nickname);

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
     * Displays a general status of the match and the player who's turn is being played
     * @param players all the players connected to the match
     * @param personalBoards all the personal boards of the players
     * @param playingPlayer the player who is playing currently
     */
    void displayStatus(List<String> players, List<PersonalBoard> personalBoards, String playingPlayer);

    /**
     * Displays the table with all his contents, including various decks and the market
     * @param t
     */
    void displayTable(Table t);

    /**
     * Displays the effect of a leader card
     * @param leaderCard the leader card's effect
     */
    void displayEffect(LeaderCard leaderCard);

    /**
     * Asks the user where to put the DevCard bought
     * @param slots the three slots in the personal board
     */
    void fetchSlotChoice(List<Slot> slots);

    /**
     * Displays that a player reached a pope space and positions will be checked
     * @param popeSpace the pope space reached
     */
    void displayPopeSpaceActivation(PopeSpace popeSpace);

    /**
     * Shows the market to the player to let him choose the line to buy
     * @param marketTray the current market tray
     */
    void displayMarket(MarketTray marketTray);


    /**
     * Ending message when a player wins
     * @param win message displayed
     */
    void displayWinningMsg(String win);

    void fetchPlayersNumber() throws IOException;
}
