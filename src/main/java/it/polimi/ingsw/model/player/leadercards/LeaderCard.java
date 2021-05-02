package it.polimi.ingsw.model.player.leadercards;

/**
 * General class of all the leader cards. It has the constructor to initialize them.
 */
public class LeaderCard {

    private int victoryPoints;
    private LeaderEffect leaderEffect;

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

    @Override
    public String toString() {
        String text;
        text = "\n-----------------------\n     " + this.victoryPoints + "\n";
        return "LeaderCard{" +
                "victoryPoints=" + victoryPoints +
                ", leaderEffect=" + leaderEffect +
                '}';
    }
}
