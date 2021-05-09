package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class GoingMarket extends Message {
    int index;
    //true row, false column
    boolean rowOrColumn;

    public GoingMarket(int index, boolean rowOrColumn) {
        super("client", "server", Content.GOING_MARKET);
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
