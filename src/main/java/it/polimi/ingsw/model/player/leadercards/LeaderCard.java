package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.model.player.Player;

public class LeaderCard {
    private int victoryPoints;
    static private String cardName;
    private LeaderEffect effect;
    private boolean isDiscarded;
    private boolean isActive;

    public LeaderCard(int victoryPoints, LeaderEffect effect, boolean isDiscarded, boolean isActive) {
        this.victoryPoints = victoryPoints;
        this.effect = effect;
        this.isDiscarded = isDiscarded;
        this.isActive = isActive;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public String getCardName() {
        return cardName;
    }

    public LeaderEffect getEffect() {
        return effect;
    }

    //in player devo cambiare isDiscarded quando il player scarta la leaderCard
    public boolean isDiscarded() {
        return isDiscarded;
    }

    public boolean isActive() {
        return isActive;
    }


    public void activateEffect(Player player) {
        isActive = true;
    }
}
