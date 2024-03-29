package it.polimi.ingsw.model.player.leadercards;

import java.io.Serializable;

/**
 * Class that models a leader card.
 */
public class LeaderCard implements Serializable {

    private final int victoryPoints;
    private final LeaderEffect leaderEffect;

    public LeaderCard(int victoryPoints, LeaderEffect leaderEffect) {
        this.victoryPoints = victoryPoints;
        this.leaderEffect = leaderEffect;
    }

    public LeaderEffect getLeaderEffect() {
        return leaderEffect;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }


}
