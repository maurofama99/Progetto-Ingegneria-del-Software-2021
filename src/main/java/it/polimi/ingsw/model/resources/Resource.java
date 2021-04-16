package it.polimi.ingsw.model.resources;

import com.google.gson.annotations.SerializedName;

/**
 * Class of the resources in the game. Some need to be stored, some are used in effects
 * Faith points are used to move the markers around
 */
public class Resource implements Cloneable{
    @SerializedName("type")
    private ResourceType type;
    private int qnt;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Resource(int qnt, ResourceType type) {
        this.type = type;
        this.qnt = qnt;
    }

    public int getQnt() {
        return qnt;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public void setQnt(int qnt) {
        this.qnt = qnt;
    }

    @Override
    public String toString() {
        if (type.equals(ResourceType.SHIELD)) return "SHIELD";
        else if (type.equals(ResourceType.COIN)) return "COIN";
        else if (type.equals(ResourceType.STONE)) return "STONE";
        else if (type.equals(ResourceType.SERVANT)) return "SERVANT";
        else if (type.equals(ResourceType.FAITHPOINT)) return "FAITHPOINT";
        else if (type.equals(ResourceType.WHITERESOURCE)) return "WHITE";
        else return "Error in Resource toString()";
    }
}
