package it.polimi.ingsw.model.resources;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.view.cli.CliColor;

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
        StringBuilder s = new StringBuilder();
        s.append(qnt);
        switch (type) {
            case COIN:
                s.append(CliColor.ANSI_YELLOW.escape()).append(" ●").append(CliColor.RESET);
                break;
            case SHIELD:
                s.append(CliColor.ANSI_BLUE.escape()).append(" ●").append(CliColor.RESET);
                break;
            case STONE:
                s.append(CliColor.ANSI_GRAY.escape()).append(" ●").append(CliColor.RESET);
                break;
            case FAITHPOINT:
                s.append(CliColor.ANSI_RED.escape()).append(" ✝").append(CliColor.RESET);
                break;
            case SERVANT:
                s.append(CliColor.ANSI_PURPLE.escape()).append(" ●").append(CliColor.RESET);
                break;
        }
        return s.toString();
    }
}
