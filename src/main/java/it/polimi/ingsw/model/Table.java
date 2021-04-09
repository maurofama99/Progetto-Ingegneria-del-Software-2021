package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.TableState;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.singleplayer.Token;

import java.rmi.MarshalException;
import java.util.ArrayList;

public class Table {
    private int numPlayers;
    private ArrayList<Player> players;
    private int currentTurn;
    private ArrayList<Token> tokenStack;
    private TableState state;
    private MarketTray marketTray;
    private Deck deck;

    public Table(int numPlayers, ArrayList<Player> players, int currentTurn, ArrayList<Token> tokenStack, TableState state, MarketTray marketTray, Deck deck) {
        this.numPlayers = numPlayers;
        this.players = players;
        this.currentTurn = currentTurn;
        this.tokenStack = tokenStack;
        this.state = state;
        this.marketTray = marketTray;
        this.deck = deck;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public ArrayList<Token> getTokenStack() {
        return tokenStack;
    }

    public MarketTray getMarketTray() {
        return marketTray;
    }

    public Deck getDeck() {
        return deck;
    }

    public TableState getState() {
        return state;
    }

    public void setState(TableState state) {
        this.state = state;
    }
}
