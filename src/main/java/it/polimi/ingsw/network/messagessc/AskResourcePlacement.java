package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class AskResourcePlacement extends Message {

    public AskResourcePlacement() {
        super("server", Content.ASK_RESOURCE_PLACEMENT);
    }
}
