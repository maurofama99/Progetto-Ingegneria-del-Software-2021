package it.polimi.ingsw.model.player.faithtrack;

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
}
