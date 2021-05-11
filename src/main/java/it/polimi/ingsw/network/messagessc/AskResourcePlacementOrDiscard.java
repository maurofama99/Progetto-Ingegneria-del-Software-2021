package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class AskResourcePlacementOrDiscard extends Message {

    public AskResourcePlacementOrDiscard() {
        super("server", "client", Content.ASK_RESOURCE_PLACEMENT_OR_DISCARD);
    }
}
