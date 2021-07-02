package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.devcard.Color;
import it.polimi.ingsw.model.devcard.Deck;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.leadercards.LeaderEffect;
import it.polimi.ingsw.model.player.leadercards.LeaderEffectJsonDeserializer;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.singleplayer.LorenzoIlMagnifico;
import it.polimi.ingsw.model.singleplayer.MoveAction;
import it.polimi.ingsw.model.singleplayer.RemoveCardsAction;
import it.polimi.ingsw.model.singleplayer.Token;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagessc.DisplayLeaderCards;
import it.polimi.ingsw.observerPattern.Observable;

import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


/**
 * Class of table, where all the stuff is placed.
 * Player, decks, stacks of token, the market and boards and cards are all here
 */
public class Table extends Observable implements Serializable{

    private static final long serialVersionUID = 719340001294526493L;

    private int numPlayers;
    private final ArrayList<Player> players = new ArrayList<>();
    private Player singlePlayer;
    private final ArrayList<LeaderCard> leaderCardsDeck;
    private Player currentPlayer;
    private ArrayList<Token> tokenStack;
    private final MarketTray marketTray;
    private final Deck devCardsDeck;
    private LorenzoIlMagnifico lorenzoIlMagnifico;
    private int topCardIndex = 15;

    public Table() {
        this.numPlayers = 0;

        Type leaderCardArrayListType = new TypeToken<ArrayList<LeaderCard>>(){}.getType();

        Gson gson = new GsonBuilder().registerTypeAdapter(LeaderEffect.class, new LeaderEffectJsonDeserializer()).create();

        leaderCardsDeck = gson.fromJson(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/LeaderCards.json"))), leaderCardArrayListType);

        this.marketTray = new MarketTray();
        this.devCardsDeck = new Deck();
    }

    public Table (Player singlePlayer){

        this.numPlayers  = 1;
        players.add(singlePlayer);
        this.currentPlayer = singlePlayer;

        this.singlePlayer = singlePlayer;


            Type leaderCardArrayListType = new TypeToken<ArrayList<LeaderCard>>(){}.getType();

            Gson gson = new GsonBuilder().registerTypeAdapter(LeaderEffect.class, new LeaderEffectJsonDeserializer()).create();

            leaderCardsDeck = gson.fromJson(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/LeaderCards.json"))), leaderCardArrayListType);


        this.lorenzoIlMagnifico = new LorenzoIlMagnifico();
        this.tokenStack = createTokenStack();

        currentPlayer.getPersonalBoard().getFaithTrack().setBlackCrossPosition(0);

        this.marketTray = new MarketTray();
        this.devCardsDeck = new Deck();
    }

    public ArrayList<LeaderCard> getLeaderCardsDeck() {
        return leaderCardsDeck;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getSinglePlayer() {
        return singlePlayer;
    }

    public void setSinglePlayer(Player singlePlayer) {
        this.singlePlayer = singlePlayer;
    }

    /**
     * Sets the number of player in game and notifies the observer about that
     * @param numPlayers how many players will play the game
     */
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public MarketTray getMarketTray() {
        return marketTray;
    }

    public LorenzoIlMagnifico getLorenzoIlMagnifico() {
        return lorenzoIlMagnifico;
    }

    public ArrayList<Token> getTokenStack() {
        return tokenStack;
    }

    public Deck getDevCardsDeck() {
        return devCardsDeck;
    }

    /**
     * Add player to the match
     * @param nickname the nickname of the player
     */
    public void addPlayer(String nickname){
        Player player = new Player(nickname);
        players.add(player);
    }

    /**
     * Method used to shuffle and set the turn order of the players.
     */
    public void setPlayersInGame(){
        Collections.shuffle(players);
        setCurrentPlayer(players.get(0));
        notifyObserver(new Message( players.get(0).getNickname(), Content.FIRST_PLAYER));
        for (Player player : players){
            player.setTurnOrder(players.indexOf(player));
        }
    }

    /**
     * Shuffle leader card deck and deals them to ach player in game.
     * @param playerNickname player that receives leaderCards
     */
    public void dealLeaderCards(String playerNickname) {
        Collections.shuffle(leaderCardsDeck);

        for (Player player : players) {
            if (player.getNickname().equals(playerNickname)) {
                for (int i = 0; i < 4; i++) {
                    player.getLeaderCards().add(leaderCardsDeck.get(topCardIndex));
                    leaderCardsDeck.remove(leaderCardsDeck.get(topCardIndex));
                    topCardIndex--;
                }
                notifyObserver(new DisplayLeaderCards(player.getLeaderCards(), playerNickname));
            }
        }
    }


    /**
     * Sets current player with the new player active in game.
     */
    public void nextPlayer(){
        if (getPlayers().indexOf(currentPlayer) < (players.size()-1))
            setCurrentPlayer(players.get(getPlayers().indexOf(currentPlayer) + 1));
        else
            setCurrentPlayer(players.get(0));
    }


    /**
     * Creates the token stack for the single player game.
     */
    public ArrayList<Token> createTokenStack(){
        tokenStack = new ArrayList<>();
        tokenStack.add(new Token(new RemoveCardsAction(Color.GREEN)));
        tokenStack.add(new Token(new RemoveCardsAction(Color.BLUE)));
        tokenStack.add(new Token(new RemoveCardsAction(Color.YELLOW)));
        tokenStack.add(new Token(new RemoveCardsAction(Color.PURPLE)));
        tokenStack.add(new Token(new MoveAction(2)));
        tokenStack.add(new Token(new MoveAction(1)));

        Collections.shuffle(tokenStack);
        lorenzoIlMagnifico.setShowedToken(tokenStack.get(0));
        return tokenStack;
    }

}
