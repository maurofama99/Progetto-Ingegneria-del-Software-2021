package it.polimi.ingsw.network.messagessc;

import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

public class AskSwapWhite extends Message {

    ResourceType type1;
    ResourceType type2;

    public AskSwapWhite(ResourceType type1, ResourceType type2) {
        super("server", "client", Content.ASK_SWAP_WHITE);
        this.type1 = type1;
        this.type2 = type2;
    }

    public ResourceType getType1() {
        return type1;
    }

    public ResourceType getType2() {
        return type2;
    }
}
