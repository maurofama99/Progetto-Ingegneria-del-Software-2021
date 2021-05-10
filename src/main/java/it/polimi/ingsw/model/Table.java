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
import it.polimi.ingsw.network.messagessc.DisplayPersonalBoard;
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
    private int numPlayers;
    private ArrayList<Player> players;
    private ArrayList<LeaderCard> leaderCardsDeck;
    private Player currentPlayer;
    private ArrayList<Token> tokenStack;
    private MarketTray marketTray;
    private Deck devCardsDeck = new Deck();
    private LorenzoIlMagnifico lorenzoIlMagnifico;

    int topCardIndex = 15;

    public Table() {
        this.numPlayers = 0;
        this.players = new ArrayList<>();


        try (Reader reader = new FileReader("src/main/resources/LeaderCards.json")) {

            Type leaderCardArrayListType = new TypeToken<ArrayList<LeaderCard>>(){}.getType();

            Gson gson = new GsonBuilder().registerTypeAdapter(LeaderEffect.class, new LeaderEffectJsonDeserializer()).create();

            leaderCardsDeck = gson.fromJson(reader, leaderCardArrayListType);


        } catch (IOException e) {
            e.printStackTrace();
        }



        //todo manca inizializzazione marketray;
        this.devCardsDeck = new Deck();
    }

    public Table (LorenzoIlMagnifico lorenzoIlMagnifico){
        this.tokenStack = new ArrayList<>(); //todo manca json token
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

    //shuffle leader cards and deal them at the beginning of the game to each player

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
        //notifyObserver con mex: quale azione?
    }


    //todo metodo che rimuove un giocatore se si disconnette


    /**
     * Creates the token stack for the single player game.
     */
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


    @Override
    public String toString() {
        return marketTray.toString() + devCardsDeck.toString();
    }
}
