package it.polimi.ingsw.model.player.faithtrack;

import it.polimi.ingsw.model.player.Player;

/**
 * This is the general Tile class. It is the base of all others tiles.
 */
public class Tile {
    private int position;
    private boolean isFirstSection;
    private boolean isSecondSection;
    private boolean isThirdSection;

    public Tile(int position, boolean isFirstSection, boolean isSecondSection, boolean isThirdSection) {
        this.position = position;
        this.isFirstSection = isFirstSection;
        this.isSecondSection = isSecondSection;
        this.isThirdSection = isThirdSection;
    }

    public boolean isFirstSection() {
        return isFirstSection;
    }

    public boolean isSecondSection() {
        return isSecondSection;
    }

    public boolean isThirdSection() {
        return isThirdSection;
    }

    public int getPosition() {
        return position;
    }

    /**
     * Method that add points when called on the track.
     * @param v Victory Tile is needed to get the points to add
     * @param p Player who will get the points
     */
    public void addPoints(Victory v, Player p){
        v.addPoints(v.getPoints(), p);
    }

    /**
     * Method called when a player reaches the pope's hats.
     * @param ps which of the three hats the marker hit
     * @param ft the track that is checked
     * @param p to get the marker to check
     */
    public void turnFavorAddPoints(PopeSpace ps, FaithTrack ft, Player p){
        ps.turnFavorAddPoints(ft, p);
    }
}
