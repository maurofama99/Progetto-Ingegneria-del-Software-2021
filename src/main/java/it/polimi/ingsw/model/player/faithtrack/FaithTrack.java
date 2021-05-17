package it.polimi.ingsw.model.player.faithtrack;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.messagessc.EndGame;
import it.polimi.ingsw.network.messagessc.TurnFavorTiles;
import it.polimi.ingsw.observerPattern.Observable;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Class that initializes and controls the track of every player. We made as every player
 * has its own track to make it easier to use the Favor Tiles
 */
public class FaithTrack extends Observable implements Serializable {
    private int faithMarkerPosition;
    private int blackCrossPosition;
    private ArrayList<Tile> track;
    private boolean firstFavorTile;
    private boolean secondFavorTile;
    private boolean thirdFavorTile;


    public FaithTrack() {
        this.faithMarkerPosition = 0;
        this.blackCrossPosition = -1;
        this.track = createTrack();
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

    public boolean isFirstFavorTile() {
        return firstFavorTile;
    }

    public boolean isSecondFavorTile() {
        return secondFavorTile;
    }

    public boolean isThirdFavorTile() {
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
     * This is the method that moves the marker forward.
     * @param faithToAdd Number of steps to make.
     */
    public void moveForward(Player p, int faithToAdd){
        setFaithMarkerPosition(faithMarkerPosition+faithToAdd);
        checkFaithMarkerPosition(p, faithMarkerPosition);
    }

    /**
     * This method initializes the track. Every tile can be modified, moved or deleted. Also creates the
     * three sections.
     */
    public ArrayList<Tile> createTrack(){
        track = new ArrayList<>();

        track.add(new Normal(0,false,false,false));
        track.add(new Normal(1, false,false,false));
        track.add(new Normal(2,false, false, false));
        track.add(new Victory(3, false,false,false,1));
        track.add(new Normal(4, false,false,false));
        track.add(new Normal(5,true, false, false));
        track.add(new Victory(6,true,false,false,1));
        track.add(new Normal(7,true,false,false));
        track.add(new PopeSpace(8, true, false,false));
        track.add(new Victory(9, false,false,false,2));
        track.add(new Normal(10,false,false,false));
        track.add(new Normal(11,false,false,false));
        track.add(new Victory(12,false,true,false,2));
        track.add(new Normal(13,false,true,false));
        track.add(new Normal(14,false,true,false));
        track.add(new Victory(15,false,true,false,3));
        track.add(new PopeSpace(16,false,true,false));
        track.add(new Normal(17,false,false,false));
        track.add(new Victory(18,false,false,false, 3));
        track.add(new Normal(19, false,false, true));
        track.add(new Normal(20,false,false,true));
        track.add(new Victory(21, false,false,true,4));
        track.add(new Normal(22, false,false,true));
        track.add(new Normal(23, false,false,true));
        track.add(new PopeSpace(24,false,false,true));

        return track;
    }

    /**
     * Adds victory points when player's faith marker reaches a tile that has victory points.
     * @param player player that needs victory points.
     * @param faithMarkerPosition player's position on Faith Track.
     */
    public void checkFaithMarkerPosition (Player player, int faithMarkerPosition){
        if (faithMarkerPosition==24){
            player.setVictoryPoints(player.getVictoryPoints() + 4);
            notifyObserver(new EndGame());
        }
        else if (faithMarkerPosition%3==0){
            track.get(faithMarkerPosition).addPoints(player);
        }

        else if (faithMarkerPosition%8==0){
            track.get(faithMarkerPosition).turnFavorAddPoints(player);
            notifyObserver(new TurnFavorTiles(player.getNickname()));
        }


    }



    @Override
    public String toString() {

        return "FAITH TRACK:\n"
                + track.toString() +
                "\n\nFAITH MARKER CURRENT POSITION: " + faithMarkerPosition;
    }
}
