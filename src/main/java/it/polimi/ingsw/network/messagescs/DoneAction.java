package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * Simple message to know the player does not want to do anything else, even if he could.
 */
public class DoneAction extends Message {

    public DoneAction() {
        super("client","server", Content.DONE_ACTION);
    }
}
