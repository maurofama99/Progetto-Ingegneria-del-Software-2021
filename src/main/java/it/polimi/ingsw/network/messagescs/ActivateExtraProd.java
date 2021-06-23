package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * Message for the extra production provided by the leader cards.
 */
public class ActivateExtraProd extends Message {
    int type;

    public ActivateExtraProd(int type) {
        super( "server", Content.ACTIVATE_EXTRAPRODUCTION);
        this.type = type;
    }

    /**
     * Checks which resource the leader card wants to activate the production
     * @return the type of resource
     */
    public ResourceType getType() {
        ResourceType resType;
        switch (type){
            case 0:
                resType = ResourceType.SHIELD;
                break;
            case 1:
                resType = ResourceType.SERVANT;
                break;
            case 2:
                resType = ResourceType.COIN;
                break;
            case 3:
                resType = ResourceType.STONE;
                break;
            case -1:
                resType = ResourceType.NULLRESOURCE;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return resType;
    }
}
