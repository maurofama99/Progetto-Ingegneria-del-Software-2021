package it.polimi.ingsw.model.player.faithtrack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.messagessc.EndGame;
import it.polimi.ingsw.network.messagessc.EndSoloGame;
import it.polimi.ingsw.network.messagessc.TurnFavorTiles;
import it.polimi.ingsw.observerPattern.Observable;

import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;


/**
 * Class that models player's faith track.
 */
public class FaithTrack extends Observable implements Serializable {
    private int faithMarkerPosition;
    private int blackCrossPosition;
    private ArrayList<Tile> track;
    private boolean firstFavorTile;
    private boolean secondFavorTile;
    private boolean thirdFavorTile;


    /**
     * Constructs player's faith track.
     */
    public FaithTrack() {
        this.faithMarkerPosition = 0;
        this.blackCrossPosition = -1;
        this.firstFavorTile = false;
        this.secondFavorTile = false;
        this.thirdFavorTile = false;

        Gson gson = new Gson();

        Type tileListType = new TypeToken<ArrayList<Tile>>() {
        }.getType();

        this.track = gson.fromJson(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles.json"))), tileListType);
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
     * Moves the player's faith marker forward.
     * @param faithToAdd Number of steps to make.
     */
    public void moveForward(Player p, int faithToAdd){
        setFaithMarkerPosition(faithMarkerPosition+faithToAdd);
        checkFaithMarkerPosition(p, faithMarkerPosition);
    }


    /**
     * Checks player's faith marker position. Based on the position it can add victory points,
     * turn favor tile or end the game
     * @param player player that owns the fiath track.
     * @param faithMarkerPosition player's position on Faith Track.
     */
    public void checkFaithMarkerPosition (Player player, int faithMarkerPosition) {
        if (faithMarkerPosition == 24) {
            player.setVictoryPoints(player.getVictoryPoints() + 4);
            notifyObserver(new TurnFavorTiles(player.getNickname()));
            notifyObserver(new EndGame(player.getTurnOrder()));
        } else if (faithMarkerPosition % 3 == 0 && faithMarkerPosition<24) {
            player.setVictoryPoints(track.get(faithMarkerPosition).getVictoryPoints() + player.getVictoryPoints());
        } else if (faithMarkerPosition % 8 == 0 && faithMarkerPosition<24) {
            notifyObserver(new TurnFavorTiles(player.getNickname()));
        }
    }


    /**
     * Checks Lorenzo's black cross position. Based on the position it can check
     * single player's favor tile or end the game.
     * @param singlePlayer single player in game.
     * @param blackCrossPosition Lorenzo's position on faith track.
     */
    public void checkBlackCrossPosition (Player singlePlayer, int blackCrossPosition) {
        if (blackCrossPosition == 24) {
            notifyObserver(new TurnFavorTiles(singlePlayer.getNickname()));
            notifyObserver(new EndSoloGame(false));
        }
        else if (blackCrossPosition % 8 == 0 && blackCrossPosition<24) {
            notifyObserver(new TurnFavorTiles(singlePlayer.getNickname()));
        }
    }

}
