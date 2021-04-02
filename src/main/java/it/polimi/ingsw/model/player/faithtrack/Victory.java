package it.polimi.ingsw.model.player.faithtrack;

import it.polimi.ingsw.model.player.Player;

public class Victory extends Tile {
    private int points;

    public Victory(int position, boolean isFirstSection, boolean isSecondSection, boolean isThirdSection, int points) {
        super(position, isFirstSection, isSecondSection, isThirdSection);
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    //add these points to the victory points of the player
    public void addPoints(int pointsToAdd, Player p) {
        
        p.setVictoryPoints(p.getVictoryPoints()+pointsToAdd);
    }
}
