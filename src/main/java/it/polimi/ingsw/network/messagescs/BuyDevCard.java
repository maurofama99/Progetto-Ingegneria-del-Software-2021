package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * This message is used to buy a dev card in the shop. The player provides the row and column where the card is
 * and where he wants to place it in his personal board, and we check if the action is possible.
 */
public class BuyDevCard extends Message {
    private int row, column;
    private int slot;


    public BuyDevCard(int row, int column, int slot) {
        super("client", "server", Content.BUY_DEVCARD);
        this.row = row;
        this.column = column;
        this.slot = slot;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getSlot() {
        return slot;
    }
}
