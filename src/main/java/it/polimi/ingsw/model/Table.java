package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.TableState;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.singleplayer.Token;
import it.polimi.ingsw.model.devcard.*;

import java.util.ArrayList;

public class Table {
    private int numPlayers;
    private ArrayList<Player> players;
    private int currentTurn;
    private ArrayList<Token> tokenStack;
    private TableState state;
    private Deck devCardsDeck;

    public Deck getDevCardsDeck() {
        return devCardsDeck;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public ArrayList<Token> getTokenStack() {
        return tokenStack;
    }

    public void setTokenStack(ArrayList<Token> tokenStack) {
        this.tokenStack = tokenStack;
    }

    public TableState getState() {
        return state;
    }

    public void setState(TableState state) {
        this.state = state;
    }
}
