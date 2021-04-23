package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.singleplayer.*;
import it.polimi.ingsw.model.devcard.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static it.polimi.ingsw.model.devcard.Color.GREEN;

/**
 * Class of table, where all the stuff is placed. Player, decks, stacks of token, the market and
 * boards and cards are all here
 */
public class Table {
    private final int numPlayers;
    private final ArrayList<Player> players;
    private int currentTurn;
    private ArrayList<Token> tokenStack;
    private MarketTray marketTray;
    private Deck devCardsDeck;
    private LorenzoIlMagnifico lorenzoIlMagnifico;

    public int getNumPlayers() {
        return numPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public MarketTray getMarketTray() {
        return marketTray;
    }
    public Table(int numPlayers, ArrayList<Player> players) {
        this.numPlayers = numPlayers;
        this.players = players;
        this.currentTurn = 0;
    }


    public LorenzoIlMagnifico getLorenzoIlMagnifico() {
        return lorenzoIlMagnifico;
    }

    public ArrayList<Token> getTokenStack() {
        return tokenStack;
    }

    public void setTokenStack(ArrayList<Token> tokenStack) {
        this.tokenStack = tokenStack;
    }

    public Deck getDevCardsDeck() {
        return devCardsDeck;
    }

    //Creates the tokenStack, will prolly be moved later to controller or smth
    public void createTokenStack(){
        tokenStack = new ArrayList<>();

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
