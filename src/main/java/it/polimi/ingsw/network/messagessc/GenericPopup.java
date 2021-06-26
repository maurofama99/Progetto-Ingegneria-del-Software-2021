package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * Similarly at the generic message, but we use this in the GUI with the popup
 */
public class GenericPopup extends Message {
    private final String message;

    /**
     * Default constructor
     * @param message the text of the popup
     */
    public GenericPopup(String message) {
        super("server", "client", Content.GENERIC_POPUP);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
