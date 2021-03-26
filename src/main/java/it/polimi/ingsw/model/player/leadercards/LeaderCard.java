package it.polimi.ingsw.model.player.leadercards;

public class LeaderCard {
    private int victoryPoints;
    static private String cardName;
    private LeaderEffect effect;
    private boolean isDiscarded;
    private boolean isActive;

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public String getCardName() {
        return cardName;
    }

    public LeaderEffect getEffect() {
        return effect;
    }

    public boolean isDiscarded() {
        return isDiscarded;
    }

    public boolean isActive() {
        return isActive;
    }
}
