package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * This message is used when the player wants to go to the market. He specifies if he's choosing a row or a column
 * and the index of this one
 */
public class GoingMarket extends Message {
    int index;
    //true row, false column
    boolean rowOrColumn;

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
