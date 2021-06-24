package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * This message is used when the player wants to go to the market. He specifies if he's choosing a row or a column
 * and the index of this one
 */
public class GoingMarket extends Message {
    int index;
    boolean rowOrColumn;

    /**
     * Default constructor
     * @param index int the defines the number of the row/column
     * @param rowOrColumn booleawn true for row, false for column
     */
    public GoingMarket(int index, boolean rowOrColumn) {
        super( "server", Content.GOING_MARKET);
        this.index = index;
        this.rowOrColumn = rowOrColumn;
    }

    public int getIndex() {
        return index;
    }

    public boolean isRowOrColumn() {
        return rowOrColumn;
    }
}
