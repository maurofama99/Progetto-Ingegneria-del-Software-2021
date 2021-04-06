package it.polimi.ingsw.model.player.faithtrack;

import it.polimi.ingsw.model.player.*;

public class PopeSpace extends Tile{
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

    //Check if player is in pope section and turns the tile if true. If false, removes the tile and gives zero
    public void turnFavorAddPoints(FaithTrack ft, Player p){
        int currentPos = p.getPlayerFaithMarker().getPosition();

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
