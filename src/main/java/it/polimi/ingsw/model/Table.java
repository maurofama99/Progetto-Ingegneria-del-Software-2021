package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.leadercards.LeaderEffect;
import it.polimi.ingsw.model.player.leadercards.LeaderEffectJsonDeserializer;
import it.polimi.ingsw.model.resources.MarketTray;
import it.polimi.ingsw.model.singleplayer.*;
import it.polimi.ingsw.model.devcard.*;
import it.polimi.ingsw.network.messagessc.DisplayLeaderCards;
import it.polimi.ingsw.network.messagessc.GenericMessage;
import it.polimi.ingsw.observerPattern.Observable;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * Class of table, where all the stuff is placed.
 * Player, decks, stacks of token, the market and boards and cards are all here
 */
public class Table extends Observable implements Serializable{

    private static final long serialVersionUID = 719340001294526493L;

    private int numPlayers;
    private ArrayList<Player> players = new ArrayList<>();
    private Player singlePlayer;
    private ArrayList<LeaderCard> leaderCardsDeck;
    private Player currentPlayer;
    private ArrayList<Token> tokenStack;
    private MarketTray marketTray;
    private Deck devCardsDeck;
    private LorenzoIlMagnifico lorenzoIlMagnifico;
    private int topCardIndex = 15;

    public Table() {
        this.numPlayers = 0;

        try (Reader reader = new FileReader("src/main/resources/LeaderCards.json")) {

            Type leaderCardArrayListType = new TypeToken<ArrayList<LeaderCard>>(){}.getType();

            Gson gson = new GsonBuilder().registerTypeAdapter(LeaderEffect.class, new LeaderEffectJsonDeserializer()).create();

            leaderCardsDeck = gson.fromJson(reader, leaderCardArrayListType);


        } catch (IOException e) {
            e.printStackTrace();
        }



        this.marketTray = new MarketTray();
        this.devCardsDeck = new Deck();
    }

    public Table (Player singlePlayer){

        this.numPlayers  = 1;
        players.add(singlePlayer);
        this.currentPlayer = singlePlayer;

        this.singlePlayer = singlePlayer;

        try (Reader reader = new FileReader("src/main/resources/LeaderCards.json")) {

            Type leaderCardArrayListType = new TypeToken<ArrayList<LeaderCard>>(){}.getType();

            Gson gson = new GsonBuilder().registerTypeAdapter(LeaderEffect.class, new LeaderEffectJsonDeserializer()).create();

            leaderCardsDeck = gson.fromJson(reader, leaderCardArrayListType);


        } catch (IOException e) {
            e.printStackTrace();
        }

        this.tokenStack = createTokenStack();
        this.lorenzoIlMagnifico = new LorenzoIlMagnifico(tokenStack.get(0));

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
        System.out.println("Number of players set, players in game:" + players.size());
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

    public void setTokenStack(ArrayList<Token> tokenStack) {
        this.tokenStack = tokenStack;
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

        notifyObserver(new GenericMessage(nickname + " has joined the game"));
    }

    /**
     * Method used to shuffle the order of the players.
     * Now TurnOrder is 1 for first player, 2 for second and so on.
     */
    public void setPlayersInGame(){
        Collections.shuffle(players);
        setCurrentPlayer(players.get(0));
        for (Player player : players){
            player.setTurnOrder(players.indexOf(player) + 1);
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



    public void nextPlayer(){
        if (getPlayers().indexOf(currentPlayer) < (players.size()-1))
            setCurrentPlayer(players.get(getPlayers().indexOf(currentPlayer) + 1));
        else
            setCurrentPlayer(players.get(0));
    }




    //todo metodo che rimuove un giocatore se si disconnette


    /**
     * Creates the token stack for the single player game.
     */
    public ArrayList<Token> createTokenStack(){
        tokenStack = new ArrayList<>();

        tokenStack.add(new Token(new RemoveCardsAction(Color.GREEN), false));
        tokenStack.add(new Token(new RemoveCardsAction(Color.BLUE), false));
        tokenStack.add(new Token(new RemoveCardsAction(Color.YELLOW), false));
        tokenStack.add(new Token(new RemoveCardsAction(Color.PURPLE), false));
        tokenStack.add(new Token(new MoveAction(2), false));
        tokenStack.add(new Token(new MoveAction(1), false));

        Collections.shuffle(tokenStack, new Random());

        return tokenStack;
    }

}
