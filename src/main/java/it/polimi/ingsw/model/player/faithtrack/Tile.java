package it.polimi.ingsw.model.player.faithtrack;

import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;

/**
 * Abstract class that models tiles of faith track.
 *
 * */
public class Tile implements Serializable {
    private final int position;
    private final boolean isFirstSection;
    private final boolean isSecondSection;
    private final boolean isThirdSection;
    private final int victoryPoints;

    public Tile(int position, boolean isFirstSection, boolean isSecondSection, boolean isThirdSection, int i) {
        this.position = position;
        this.isFirstSection = isFirstSection;
        this.isSecondSection = isSecondSection;
        this.isThirdSection = isThirdSection;
        this.victoryPoints =i;
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

    public int getVictoryPoints() {
        return victoryPoints;
    }


    /**
     * This method is used for checking if a player's FaithMarker is in range to turn the Favor Tile. It
     * also adds the points if it is possible
     * @param p Player whose FaithMarker's position is being checked
     */

    public void turnFavorAddPoints(Player p, int currentPos){

        if(isFirstSection && !(p.getPersonalBoard().getFaithTrack().isFirstFavorTile())){
            p.getPersonalBoard().getFaithTrack().setFirstFavorTile(true);
            if(p.getPersonalBoard().getFaithTrack().getTrack().get(currentPos).isFirstSection()) {
                p.setVictoryPoints(p.getVictoryPoints() + 2);
                p.getPersonalBoard().getFaithTrack().setFirstFavorTile(true);
            }
        }
        else if(isSecondSection && !(p.getPersonalBoard().getFaithTrack().isSecondFavorTile())){
            p.getPersonalBoard().getFaithTrack().setSecondFavorTile(true);
            if(p.getPersonalBoard().getFaithTrack().getTrack().get(currentPos).isSecondSection()) {
                p.setVictoryPoints(p.getVictoryPoints()+3);
            }
        }
        else if(isThirdSection && !(p.getPersonalBoard().getFaithTrack().isThirdFavorTile())){
            p.getPersonalBoard().getFaithTrack().setThirdFavorTile(true);
            if(p.getPersonalBoard().getFaithTrack().getTrack().get(currentPos).isThirdSection()) {
                p.setVictoryPoints(p.getVictoryPoints() + 4);
            }
        }
    }


    @Override
    public String toString() {
        return String.valueOf(position);
    }
}
