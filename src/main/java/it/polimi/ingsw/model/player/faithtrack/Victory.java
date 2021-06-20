package it.polimi.ingsw.model.player.faithtrack;

import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;

/**
 * Class that extends {@link Tile}. It models a victory tile that adds victory points to the player.
 */

public class Victory extends Tile implements Serializable {
    private final int points;

    public Victory(int position, boolean isFirstSection, boolean isSecondSection, boolean isThirdSection, int points) {
        super(position, isFirstSection, isSecondSection, isThirdSection);
        this.points = points;
    }

    /**
     * Method that adds the points when the marker hits the spot.
     * @param p to whom we need to add the points.
     */
    @Override
    public void addPoints(Player p) {
        p.setVictoryPoints(p.getVictoryPoints()+points);
    }

}
