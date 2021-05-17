package it.polimi.ingsw.model.player.faithtrack;

import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;

/**
 * This is the general Tile class. It is the base of all others tiles.
 */
public abstract class Tile implements Serializable {
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
     * @param p Player who will get the points
     */
    public abstract void addPoints(Player p);


    /**
     * This method is used for checking if a player's FaithMarker is in range to turn the Favor Tile. It
     * also adds the points if it is possible
     * @param p Player whose FaithMarker's position is being checked
     */

    public void turnFavorAddPoints(Player p){
        int currentPos = p.getPersonalBoard().getFaithTrack().getFaithMarkerPosition();

        if(isFirstSection && !(p.getPersonalBoard().getFaithTrack().isFirstFavorTile())){
            p.getPersonalBoard().getFaithTrack().setFirstFavorTile(true);
            if(p.getPersonalBoard().getFaithTrack().getTrack().get(currentPos).isFirstSection()) {
                p.setVictoryPoints(p.getVictoryPoints() + 2);
                p.getPersonalBoard().getFaithTrack().setFirstFavorTile(true);
            }
            else {
                System.out.println("Not in range");
            }
        }
        else if(isSecondSection && !(p.getPersonalBoard().getFaithTrack().isSecondFavorTile())){
            p.getPersonalBoard().getFaithTrack().setSecondFavorTile(true);
            if(p.getPersonalBoard().getFaithTrack().getTrack().get(currentPos).isSecondSection()) {
                p.setVictoryPoints(p.getVictoryPoints()+3);
            }
            else {
                System.out.println("Not in range");
            }
        }
        else if(isThirdSection && !(p.getPersonalBoard().getFaithTrack().isThirdFavorTile())){
            p.getPersonalBoard().getFaithTrack().setThirdFavorTile(true);
            if(p.getPersonalBoard().getFaithTrack().getTrack().get(currentPos).isThirdSection()) {
                p.setVictoryPoints(p.getVictoryPoints() + 4);
            }
            else {
                System.out.println("Not in range");
            }

        }
    }


    @Override
    public String toString() {
        return String.valueOf(position);
    }
}
