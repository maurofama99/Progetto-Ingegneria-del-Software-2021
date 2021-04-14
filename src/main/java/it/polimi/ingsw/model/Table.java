package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.TableState;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.singleplayer.*;
import it.polimi.ingsw.model.devcard.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static it.polimi.ingsw.model.devcard.Color.GREEN;

public class Table {
    private int numPlayers;
    private ArrayList<Player> players;
    private int currentTurn;
    private ArrayList<Token> tokenStack;
    private TableState state;
    private Deck devCardsDeck;
    private LorenzoIlMagnifico lorenzoIlMagnifico;
    private MarketTray marketTray;

    public Table(int numPlayers, ArrayList<Player> players, int currentTurn) {
        this.numPlayers = numPlayers;
        this.players = players;
        this.currentTurn = currentTurn;
    }

    public MarketTray getMarketTray() {
        return marketTray;
    }

    public void setMarketTray(MarketTray marketTray) {
        this.marketTray = marketTray;
    }

    public void setDevCardsDeck(Deck devCardsDeck) {
        this.devCardsDeck = devCardsDeck;
    }

    public LorenzoIlMagnifico getLorenzoIlMagnifico() {
        return lorenzoIlMagnifico;
    }

    public void setLorenzoIlMagnifico(LorenzoIlMagnifico lorenzoIlMagnifico) {
        this.lorenzoIlMagnifico = lorenzoIlMagnifico;
    }

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


    //Creates the tokenStack, will prolly be moved later to controller or smth
    public void createTokenStack(){
        tokenStack = new ArrayList<Token>();

        Token token0 = new Token(new RemoveCardsAction(Color.GREEN), false );
        Token token1 = new Token(new RemoveCardsAction(Color.BLUE), false);
        Token token2 = new Token(new RemoveCardsAction(Color.YELLOW), false);
        Token token3 = new Token(new RemoveCardsAction(Color.PURPLE), false);
        Token token4 = new Token(new MoveAction(2), false);
        Token token5 = new Token(new MoveAction(1), false);

        tokenStack.add(token0);
        tokenStack.add(token1);
        tokenStack.add(token2);
        tokenStack.add(token3);
        tokenStack.add(token4);
        tokenStack.add(token5);

        Collections.shuffle(tokenStack, new Random());
    }
}
