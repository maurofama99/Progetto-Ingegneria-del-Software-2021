package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class handles the player that enters in the game and acts differently based on how many are joining. Manages multiple games functionality with array lists of players that work as waiting rooms.
 * There are four waiting rooms, one for each possible number of players.
 */
public class WaitingRoom {

    private final ArrayList<Player> singlePlayerArray = new ArrayList<>();
    private final ArrayList<Player> twoPlayersArray = new ArrayList<>();
    private final ArrayList<Player> threePlayersArray = new ArrayList<>();
    private final ArrayList<Player> fourPlayersArray = new ArrayList<>();
    private final HashMap<String, VirtualView> vvMap = new HashMap<>();
    private  HashMap<String, ClientHandler> playerClientHandlerHashMap = new HashMap<>();

    public HashMap<String, ClientHandler> getPlayerClientHandlerHashMap() {
        return playerClientHandlerHashMap;
    }

    public HashMap<String, VirtualView> getVvMap() {
        return vvMap;
    }

    public ArrayList<Player> getSinglePlayerArray() {
        return singlePlayerArray;
    }

    public ArrayList<Player> getTwoPlayersArray() {
        return twoPlayersArray;
    }

    public ArrayList<Player> getThreePlayersArray() {
        return threePlayersArray;
    }

    public ArrayList<Player> getFourPlayersArray() {
        return fourPlayersArray;
    }

    /**
     * Manages login data and the first connection of the players.
     * @param msg Message received from the client.
     * @throws IOException If virtual view fails to send message.
     */
    public void receiveMessage(Message msg) throws IOException {

        VirtualView vv = vvMap.get(msg.getSenderUser());

        if (msg.getMessageType() == Content.LOGIN_DATA) {
            playerInWait(((LoginData) msg).getNickname(), ((LoginData) msg).getNumPlayers());
            boolean bool = checkGameStart();
            if (!bool) {
                vv.displayGenericMessage("Please wait for other players to join...");
                vv.displayPopup("Please wait for other players to join...");
            }
        } else throw new InvalidObjectException("Received message should be Login Data.");

    }

    /**
     * Check if a waiting room is full and starts the game for the players in it.
     * @return True if a game is starting.
     * @throws IOException If virtual view fails to send message.
     */
    public boolean checkGameStart() throws IOException {
        if (singlePlayerArray.size()==1){
            setupSingleController(singlePlayerArray);
            singlePlayerArray.clear();
            return true;
        }
        if (twoPlayersArray.size()==2){
            setupGameController(twoPlayersArray);
            twoPlayersArray.clear();
            return true;
        }
        if (threePlayersArray.size()==3){
            setupGameController(threePlayersArray);
            threePlayersArray.clear();
            return true;
        }
        if (fourPlayersArray.size()==4){
            setupGameController(fourPlayersArray);
            fourPlayersArray.clear();
            return true;
        }
        return false;
    }

    /**
     * Sets all the variables in the single player controller class in order to start the game.
     * @param players Array List of players in the waiting room.
     * @throws IOException If virtual view fails to send message.
     */
    public void setupSingleController(ArrayList<Player> players) throws IOException{
        SinglePlayerController singlePlayerController = new SinglePlayerController(players.get(0));
        singlePlayerController.getTable().addObserver(vvMap.get(players.get(0).getNickname()));
        singlePlayerController.setVirtualView(vvMap.get(players.get(0).getNickname()));

        singlePlayerController.setGameController(new GameController(singlePlayerController));
        singlePlayerController.getGameController().setTable(singlePlayerController.getTable());

        HashMap<String, VirtualView> result = new HashMap<>();
        result.put(players.get(0).getNickname(), singlePlayerController.getVirtualView());
        singlePlayerController.getGameController().setVvMap(result);

        playerClientHandlerHashMap.get(players.get(0).getNickname()).setSinglePlayer(true);
        playerClientHandlerHashMap.get(players.get(0).getNickname()).setSinglePlayerController(singlePlayerController);

        singlePlayerController.setUpSingleGame();
    }

    /**
     * Sets all the variables in the player controller class in order to start the game.
     * @param playersArray Array List of players in the waiting room.
     * @throws IOException If virtual view fails to send message.
     */
    public void setupGameController(ArrayList<Player> playersArray) throws IOException {
        GameController gc = new GameController();
        gc.setTable(new Table());
        gc.setVvMap(getPlayersVirtualView(playersArray));
        for(Player player : playersArray){
            gc.getTable().addPlayer(player.getNickname());
            gc.getTable().addObserver(vvMap.get(player.getNickname()));
            gc.getTable().setNumPlayers(playersArray.size());
        }
        for(Player player : playersArray){
            playerClientHandlerHashMap.get(player.getNickname()).setGameController(gc);
        }
        gc.startGame();
    }

    /**
     * Adds the player to the waiting room corresponding to the number of player he choose.
     * @param nickname Nickname of the player.
     * @param numPlayers Desired number of player of the game by the player.
     */
    public void playerInWait(String nickname, int numPlayers) {
        switch (numPlayers){
            case 1:
                singlePlayerArray.add(new Player(nickname));
                break;
            case 2:
                twoPlayersArray.add(new Player(nickname));
                break;
            case 3:
                threePlayersArray.add(new Player(nickname));
                break;
            case 4:
                fourPlayersArray.add(new Player(nickname));
                break;
        }
    }

    /**
     * Check if the chosen nickname is already taken by another player.
     * @param nickname Nickname chosen by the player
     * @return True if the nickname is already taken.
     */
    public boolean nicknameAlreadyPresent(String nickname){
        return (singlePlayerArray.stream().anyMatch(o -> o.getNickname().equals(nickname)) ||
                twoPlayersArray.stream().anyMatch(o -> o.getNickname().equals(nickname)) ||
                threePlayersArray.stream().anyMatch(o -> o.getNickname().equals(nickname)) ||
                fourPlayersArray.stream().anyMatch(o -> o.getNickname().equals(nickname)));
    }

    /**
     *
     * @param players Players whose virtual view we want.
     * @return An Hash Map that associates for every player is virtual view.
     */
    public HashMap<String, VirtualView> getPlayersVirtualView(ArrayList<Player> players){
        HashMap<String, VirtualView> result = new HashMap<>();
        for(Player player : players) {
            result.put(player.getNickname(), vvMap.get(player.getNickname()));
        }
        return result;
    }
}
