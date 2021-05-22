package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class GenericPopup extends Message {
    private final String message;

    public GenericPopup(String message) {
        super("server", "client", Content.GENERIC_POPUP);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
