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
    /*public void turnFavorAddPoints(int currentPosition){
        if(getSection()==1){

        }
        else if(getSection()==2){

        }
        else if(getSection()==3){

        }
    }*/
}
