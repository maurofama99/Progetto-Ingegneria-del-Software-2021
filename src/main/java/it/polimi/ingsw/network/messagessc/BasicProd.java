package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * Message for the basic prod in the GUI, sent three times and every time it spawn a different arrow
 */
public class BasicProd extends Message {

    private final int arrow;
    private final String message;

    public BasicProd(int arrow, String message) {
        super("server", "client", Content.BASIC_PROD);
        this.arrow = arrow;
        this.message = message;
    }

    public int getArrow() {
        return arrow;
    }

    public String getMessage() {
        return message;
    }
}
