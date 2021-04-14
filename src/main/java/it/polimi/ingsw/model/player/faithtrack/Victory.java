package it.polimi.ingsw.model.player.faithtrack;

import it.polimi.ingsw.model.player.Player;

/**
 * Tiles that give VPs.
 */
public class Victory extends Tile {
    private int points;

    public Victory(int position, boolean isFirstSection, boolean isSecondSection, boolean isThirdSection, int points) {
        super(position, isFirstSection, isSecondSection, isThirdSection);
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    /**
     * Method that adds the points when the marker hits the spot
      * @param pointsToAdd how many points to add
     * @param p to whom we need to add the points
     */
    public void addPoints(int pointsToAdd, Player p) {
        
        p.setVictoryPoints(p.getVictoryPoints()+pointsToAdd);
    }
}
