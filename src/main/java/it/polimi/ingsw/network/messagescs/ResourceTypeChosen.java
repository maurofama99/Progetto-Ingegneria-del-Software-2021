package it.polimi.ingsw.network.messagescs;

import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;

/**
 * The message that let the server know what resource the player has chosen for specific actions (such as production
 * with the possibility of choosing, initial bonus...)
 */
public class ResourceTypeChosen extends Message {
    int resourceType;

    public ResourceTypeChosen(String senderUser, int resourceType) {
        super(senderUser, "server", Content.RESOURCE_TYPE);
        this.resourceType = resourceType;
    }

    public ResourceType getResourceType() {
        ResourceType type;
        switch (resourceType){
            case 0:
                type = ResourceType.SHIELD;
                break;
            case 1:
                type = ResourceType.SERVANT;
                break;
            case 2:
                type = ResourceType.COIN;
                break;
            case 3:
                type = ResourceType.STONE;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + resourceType);
        }

        return type;
    }

    @Override
    public String toString() {
        String text;
        switch (resourceType){
            case 0 :
                text = "SHIELD";
                break;
            case 1:
                text = "SERVANT";
                break;
            case 2:
                text = "COIN";
                break;
            case 3:
                text = "STONE";
                break;

            default:
                text = "This resource does not exists";
        }
        return text;
    }
}
