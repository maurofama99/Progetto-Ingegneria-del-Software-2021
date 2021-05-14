package it.polimi.ingsw.model.player.leadercards;

import java.io.Serializable;

/**
 * General class of all the leader cards. It has the constructor to initialize them.
 */
public class LeaderCard implements Serializable {

    private int victoryPoints;
    private  LeaderEffect leaderEffect;

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
