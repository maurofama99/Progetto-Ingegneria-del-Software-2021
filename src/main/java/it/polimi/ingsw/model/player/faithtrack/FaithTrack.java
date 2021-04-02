package it.polimi.ingsw.model.player.faithtrack;

import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;


public class FaithTrack {
    private FaithMarker faithMarker;
    private BlackCross blackCross;
    private ArrayList<Tile> track;
    private ArrayList<Tile> firstSection;
    private ArrayList<Tile> secondSection;
    private ArrayList<Tile> thirdSection;
    private boolean firstFavorTile=false;
    private boolean secondFavorTile=false;
    private boolean thirdFavorTile=false;


    public FaithMarker getFaithMarker() {
        return faithMarker;
    }

    public BlackCross getBlackCross() {
        return blackCross;
    }

    public ArrayList<Tile> getTrack() {
        return track;
    }

    public ArrayList<Tile> getFirstSection() {
        return firstSection;
    }

    public ArrayList<Tile> getSecondSection() {
        return secondSection;
    }

    public ArrayList<Tile> getThirdSection() {
        return thirdSection;
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

    public boolean checkPosition(Player player){
        return true;
    }

    //Creates the track. Needs to know where to put the different tiles
    public void createTrack(){
        track = new ArrayList<Tile>();

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

        //Create the sections and adds to Lists
        firstSection = new ArrayList<Tile>();
        secondSection = new ArrayList<Tile>();
        thirdSection = new ArrayList<Tile>();
        firstSection.add(tile5);
        firstSection.add(tile6);
        firstSection.add(tile7);
        firstSection.add(tile8);
        secondSection.add(tile12);
        secondSection.add(tile13);
        secondSection.add(tile14);
        secondSection.add(tile15);
        secondSection.add(tile16);
        thirdSection.add(tile19);
        thirdSection.add(tile20);
        thirdSection.add(tile21);
        thirdSection.add(tile22);
        thirdSection.add(tile23);
        thirdSection.add(tile24);
        //End of create sections
    }





}
