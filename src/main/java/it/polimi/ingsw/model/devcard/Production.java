package it.polimi.ingsw.model.devcard;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * This is the class Production. It is used for initializing dev. cards and put the production
 * attribute in them.
 */
public class Production implements Serializable {
    private static final long serialVersionUID = -7532159946421192623L;

    private final String productionName;
    private ArrayList<Resource> input;
    private final ArrayList<Resource> output;

    public ArrayList<Resource> getInput() {
        return input;
    }

    public ArrayList<Resource> getOutput() {
        return output;
    }

    public Production(String productionName, ArrayList<Resource> input, ArrayList<Resource> output) {
        this.productionName = productionName;
        this.input = input;
        this.output = output;
    }

    public void setInput(ArrayList<Resource> input) {
        this.input = input;
    }

    /**
     * This method checks if the player has enough resources to start the production.
     * @param player for knowing where to check from
     * @return true if the requirements are met.
     */
    public boolean checkInputResource(Player player) {
        try {
            player.getPersonalBoard().getWarehouse().removeResources(input);
        } catch (NoSuchElementException | CloneNotSupportedException ex){
            return false;}

        return true;
    }

}
