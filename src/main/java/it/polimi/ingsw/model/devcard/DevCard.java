package it.polimi.ingsw.model.devcard;


import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is the class of the single dev. card. Every card has its own stats to get initialized
 */
public class DevCard implements Serializable{

    private static final long serialVersionUID = -2556498201540838238L;

    private int level;
    @SerializedName("cardColor")
    private Color cardColor;
    private int victoryPointsDevCard;
    private boolean isPlaced;
    private ArrayList<Resource> requirementsDevCard;
    private Production production;

    public DevCard(int level, Color color, int victoryPointsDevCard, ArrayList<Resource> requirementsDevCard, Production production) {
        this.level = level;
        this.cardColor = color;
        this.victoryPointsDevCard = victoryPointsDevCard;
        this.isPlaced = false;
        this.requirementsDevCard = requirementsDevCard;
        this.production = production;
    }

    public void setProduction(Production production) {
        this.production = production;
    }

    public int getLevel() {
        return level;
    }

    public Color getCardColor() {
        return cardColor;
    }

    public int getVictoryPointsDevCard() {
        return victoryPointsDevCard;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public ArrayList<Resource> getRequirementsDevCard() {
        return requirementsDevCard;
    }

    public Production getProduction() {
        return production;
    }

    /**
     * This method is used to check if a player can activate a production or not.
     * @param player is where the method will check for resources, both from his strongbox and deposit
     * @return true if the requirements are met, false otherwise
     */
    public boolean checkRequirements(ArrayList<Resource> requirements, Player player) throws CloneNotSupportedException {
        try {
            player.getPersonalBoard().getWarehouse().removeResources(requirements);
        }
        catch (NoSuchElementException e){
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return cardColor + "CARD of LEVEL "+ level+ " ";
    }
}

