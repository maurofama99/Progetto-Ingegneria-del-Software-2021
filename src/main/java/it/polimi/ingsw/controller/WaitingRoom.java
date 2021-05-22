package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class WaitingRoom {

    private ArrayList<Player> singlePlayerArray = new ArrayList<>();
    private ArrayList<Player> twoPlayersArray = new ArrayList<>();
    private ArrayList<Player> threePlayersArray = new ArrayList<>();
    private ArrayList<Player> fourPlayersArray = new ArrayList<>();
    private HashMap<String, VirtualView> vvMap = new HashMap<>();
    private HashMap<Player, GameController> gameControllerHashMap = new HashMap<>();
    private HashMap<String, ClientHandler> playerClientHandlerHashMap = new HashMap<>();

    public HashMap<String, ClientHandler> getPlayerClientHandlerHashMap() {
        return playerClientHandlerHashMap;
    }

    public HashMap<String, VirtualView> getVvMap() {
        return vvMap;
    }

    public void receiveMessage(Message msg) throws IOException {

        VirtualView vv = vvMap.get(msg.getSenderUser());

        switch (msg.getMessageType()){
            case LOGIN_DATA:
                //aggiungi all'arraylist corrispondente il player
                playerInWait(((LoginData)msg).getNickname(), ((LoginData)msg).getNumPlayers());
                //controlla se puoi far iniziare un game
                boolean bool = checkGameStart();
                if (!bool) vv.displayGenericMessage("Please wait for other players to join...");
                break;
        }
    }

    private boolean checkGameStart() throws IOException {
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

    public void setupSingleController(ArrayList<Player> players) throws IOException{
        SinglePlayerController singlePlayerController = new SinglePlayerController(players.get(0));
        singlePlayerController.getTable().addObserver(vvMap.get(players.get(0).getNickname()));
        singlePlayerController.setVirtualView(vvMap.get(players.get(0).getNickname()));

        singlePlayerController.setGameController(new GameController(singlePlayerController));
        singlePlayerController.getGameController().setTable(singlePlayerController.getTable());

        HashMap<String, VirtualView> result = new HashMap<>();
        result.put(players.get(0).getNickname(), singlePlayerController.getVirtualView());
        singlePlayerController.getGameController().setVvMap(result);

        playerClientHandlerHashMap.get(players.get(0).getNickname()).setStarted(true);
        playerClientHandlerHashMap.get(players.get(0).getNickname()).setSinglePlayer(true);
        playerClientHandlerHashMap.get(players.get(0).getNickname()).setSinglePlayerController(singlePlayerController);

        singlePlayerController.setUpSingleGame();
    }

    public void setupGameController(ArrayList<Player> playersArray) throws IOException {
        //crea game controller e butta dentro i player dell'array
        GameController gc = new GameController();
        gc.setTable(new Table());
        gc.setVvMap(getPlayersVirtualView(playersArray));
        //associo ad ogni player il game controller
        for (Player player : playersArray){
            gameControllerHashMap.put(player, gc);
        }
        //setta il tavolo del game controller
        for(Player player : playersArray){
            gc.getTable().addPlayer(player.getNickname());
            gc.getTable().addObserver(vvMap.get(player.getNickname()));
            gc.getTable().setNumPlayers(playersArray.size());
        }
        //setta i client handler dei player
        for(Player player : playersArray){
            playerClientHandlerHashMap.get(player.getNickname()).setStarted(true);
            playerClientHandlerHashMap.get(player.getNickname()).setGameController(gc);
        }
        gc.startGame();
    }

    private void playerInWait(String nickname, int numPlayers) {
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

    public boolean nicknameAlreadyPresent(String nickname){
        return (singlePlayerArray.stream().anyMatch(o -> o.getNickname().equals(nickname)) ||
                twoPlayersArray.stream().anyMatch(o -> o.getNickname().equals(nickname)) ||
                threePlayersArray.stream().anyMatch(o -> o.getNickname().equals(nickname)) ||
                fourPlayersArray.stream().anyMatch(o -> o.getNickname().equals(nickname)));
    }

    public HashMap<String, VirtualView> getPlayersVirtualView(ArrayList<Player> players){
        HashMap<String, VirtualView> result = new HashMap<>();
        for(Player player : players) {
            result.put(player.getNickname(), vvMap.get(player.getNickname()));
        }
        return result;
    }
}
