package it.polimi.ingsw.model.devcard;

import it.polimi.ingsw.model.resources.Resource;

import java.util.ArrayList;

public class DevCard {
    private ArrayList<Resource> cost;
    private int level;
    private Color cardColor;
    private int victoryPointsDevCard;
    private boolean isPlaced;
    private ArrayList<Resource> requirementsDevCard;
    private Production production;

    public DevCard(ArrayList<Resource> cost, int level, Color cardColor, int victoryPointsDevCard, boolean isPlaced, ArrayList<Resource> requirementsDevCard, Production production) {
        this.cost = cost;
        this.level = level;
        this.cardColor = cardColor;
        this.victoryPointsDevCard = victoryPointsDevCard;
        this.isPlaced = isPlaced;
        this.requirementsDevCard = requirementsDevCard;
        this.production = production;
    }

    public ArrayList<Resource> getCost() {
        return cost;
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


}
