package it.polimi.ingsw.model.resources;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Class of the resources in the game. Some need to be stored, some are used in effects
 * Faith points are used to move the markers around
 */
public class Resource implements Cloneable, Serializable {

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

        switch (type) {
            case COIN:
                return qnt + "$";
            case SHIELD:
                return qnt + "♦︎";
            case STONE:
                return qnt + "◼︎";
            case FAITHPOINT:
                return qnt + "+";
            case SERVANT:
                return qnt + "₷";
            case WHITERESOURCE:
                return qnt + " ";
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

}
//⬇︎⬇♦
