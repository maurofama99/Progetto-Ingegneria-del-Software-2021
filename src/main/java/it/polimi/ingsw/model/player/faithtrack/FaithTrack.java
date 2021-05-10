package it.polimi.ingsw.model.player.faithtrack;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.singleplayer.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class that initializes and controls the track of every player. We made as every player
 * has its own track to make it easier to use the Favor Tiles
 */
public class FaithTrack implements Serializable {
    private int faithMarkerPosition;
    private int blackCrossPosition;
    private ArrayList<Tile> track;
    private boolean firstFavorTile;
    private boolean secondFavorTile;
    private boolean thirdFavorTile;


    public FaithTrack() {
        this.faithMarkerPosition = -1;
        this.blackCrossPosition = -1;
        this.track = new ArrayList<>();
        this.firstFavorTile = false;
        this.secondFavorTile = false;
        this.thirdFavorTile = false;



    }

    public int getFaithMarkerPosition() {
        return faithMarkerPosition;
    }

    public int getBlackCrossPosition() {
        return blackCrossPosition;
    }

    public void setBlackCrossPosition(int blackCrossPosition) {
        this.blackCrossPosition = blackCrossPosition;
    }

    public void setFaithMarkerPosition(int faithMarkerPosition) {
        this.faithMarkerPosition = faithMarkerPosition;
    }


    public ArrayList<Tile> getTrack() {
        return track;
    }



    public boolean getIsFirstFavorTile() {
        return firstFavorTile;
    }

    public boolean getIsSecondFavorTile() {
        return secondFavorTile;
    }

    public boolean getIsThirdFavorTile() {
        return thirdFavorTile;
    }

    public void setFirstFavorTile(boolean firstFavorTile) {
        this.firstFavorTile = firstFavorTile;
    }

    public void setSecondFavorTile(boolean secondFavorTile) {
        this.secondFavorTile = secondFavorTile;
    }

    public void setThirdFavorTile(boolean thirdFavorTile) {
        this.thirdFavorTile = thirdFavorTile;
    }


    /**
     * This is the method that is called when faith points are produced. It moved the single marker forward
     * @param faithToAdd Number of steps to make.
     */
    public void moveForward(int faithToAdd){
        //Function that moves the marker forward. Needs to be connected to FaithPnt
        setFaithMarkerPosition(faithMarkerPosition+faithToAdd);
    }




    /**
     * This method initializes the track. Every tile can be modified, moved or deleted. Also creates the
     * three sections.
     */
    public void createTrack(){
        track = new ArrayList<>();

        //Create the tiles. They can be easily modified.
        Tile tile0 = new Normal(0,false,false,false);
        Tile tile1 = new Normal(1, false,false,false);
        Tile tile2 = new Normal(2,false, false, false);
        Tile tile3 = new Victory(3, false,false,false,1);
        Tile tile4 = new Normal(4, false,false,false);
        Tile tile5 = new Normal(5,true, false, false);
        Tile tile6 = new Victory(6,true,false,false,2);
        Tile tile7 = new Normal(7,true,false,false);
        Tile tile8 = new PopeSpace(8, true, false,false,1,2);
        Tile tile9 = new Victory(9, false,false,false,4);
        Tile tile10 = new Normal(10,false,false,false);
        Tile tile11 = new Normal(11,false,false,false);
        Tile tile12 = new Victory(12,false,true,false,6);
        Tile tile13 = new Normal(13,false,true,false);
        Tile tile14 = new Normal(14,false,true,false);
        Tile tile15 = new Victory(15,false,true,false,9);
        Tile tile16 = new PopeSpace(16,false,true,false,2,3);
        Tile tile17 = new Normal(17,false,false,false);
        Tile tile18 = new Victory(18,false,false,false, 12);
        Tile tile19 = new Normal(19, false,false, true);
        Tile tile20 = new Normal(20,false,false,true);
        Tile tile21 = new Victory(21, false,false,true,16);
        Tile tile22 = new Normal(22, false,false,true);
        Tile tile23 = new Normal(23, false,false,true);
        Tile tile24 = new PopeSpace(24,false,false,true, 3, 4);
        //End of creating tiles

        //Creates the track. Tiles are in order
        track.add(tile0);
        track.add(tile1);
        track.add(tile2);
        track.add(tile3);
        track.add(tile4);
        track.add(tile5);
        track.add(tile6);
        track.add(tile7);
        track.add(tile8);
        track.add(tile9);
        track.add(tile10);
        track.add(tile11);
        track.add(tile12);
        track.add(tile13);
        track.add(tile14);
        track.add(tile15);
        track.add(tile16);
        track.add(tile17);
        track.add(tile18);
        track.add(tile19);
        track.add(tile20);
        track.add(tile21);
        track.add(tile22);
        track.add(tile23);
        track.add(tile24);
        //End of add


    }


    @Override
    public String toString() {

        return "FAITH TRACK:\n"
                + track.stream().map( Tile::toString ).collect( Collectors.toList() ) +
                "\n\nFAITH MARKER CURRENT POSITION: " + faithMarkerPosition;
    }
}
