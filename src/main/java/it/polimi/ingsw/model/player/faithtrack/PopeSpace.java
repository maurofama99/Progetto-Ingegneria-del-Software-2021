package it.polimi.ingsw.model.player.faithtrack;

import it.polimi.ingsw.model.player.*;

import java.io.Serializable;

/**
 * Pope space class, the tiles with the pope's hat on them to be clear. It decides if a player is
 * in range to turn the favor tile and get some VP.
 */
public class PopeSpace extends Tile implements Serializable {
    private int section;
    private int points;

    public PopeSpace(int position, boolean isFirstSection, boolean isSecondSection, boolean isThirdSection, int section, int points) {
        super(position, isFirstSection, isSecondSection, isThirdSection);
        this.section = section;
        this.points = points;
    }

    public int getSection() {
        return section;
    }


    public int getPoints() {
        return points;
    }

    /**
     * This method is used for checking if a player's FaithMarker is in range to turn the Favor Tile. It
     * also adds the points if it is possible
     * @param ft The track of the player whose position is being checked.
     * @param p Player whose FaithMarker's position is being checked
     */
    public void turnFavorAddPoints(FaithTrack ft, Player p){
        int currentPos = p.getPersonalBoard().getFaithTrack().getFaithMarkerPosition();

        if(getSection()==1){
            if(ft.getTrack().get(currentPos).isFirstSection()) {
                p.setVictoryPoints(p.getVictoryPoints() + getPoints());
                ft.setFirstFavorTile(true);
            }
            else {
                System.out.println("Not in range");
                ft.setFirstFavorTile(false);
            }
        }
        else if(getSection()==2){
            if(ft.getTrack().get(currentPos).isSecondSection()) {
                p.setVictoryPoints(p.getVictoryPoints()+getPoints());
                ft.setSecondFavorTile(true);
            }
            else {
                System.out.println("Not in range");
                ft.setSecondFavorTile(false);
            }
        }
        else if(getSection()==3){
            if(ft.getTrack().get(currentPos).isThirdSection()) {
                p.setVictoryPoints(p.getVictoryPoints() + getPoints());
                ft.setThirdFavorTile(true);
            }
            else {
                System.out.println("Not in range");
                ft.setThirdFavorTile(false);
            }
        }
    }
}
