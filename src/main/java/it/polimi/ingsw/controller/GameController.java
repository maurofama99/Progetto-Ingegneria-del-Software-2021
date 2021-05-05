package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.network.messagescs.PlayersNumber;
import it.polimi.ingsw.network.messagescs.ResourcePlacement;
import it.polimi.ingsw.network.messagescs.ResourceTypeChosen;
import it.polimi.ingsw.network.messagessc.GenericMessage;
import it.polimi.ingsw.observerPattern.Observable;
import it.polimi.ingsw.view.VirtualView;
import java.util.concurrent.atomic.AtomicBoolean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class GameController  {
    private Player activePlayer;

    private Table table;
    private TableState tableState = TableState.WAITING_FOR_FIRSTPLAYER;

    private HashMap<String, VirtualView> vvMap = new HashMap<>();
    private AtomicBoolean firstPlayer = new AtomicBoolean(true);

    public void setVvMap(HashMap<String, VirtualView> vvMap) {
        this.vvMap = vvMap;
    }

    public HashMap<String, VirtualView> getVvMap() {
        return vvMap;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setTableState(TableState tableState) {
        this.tableState = tableState;
    }

    public void receiveMessage(Message msg) throws IOException {

        switch (tableState) {
            /*case WAITING_FOR_FIRSTPLAYER:
                receiveMessageOnFirstLogin(msg);
                break;
            case WAITING:
                receiveMessageOnLogin(msg);
                break;*/
            case SETUP:
                receiveMessageOnSetup(msg);
                break;
            case IN_GAME:
                receiveMessageInGame(msg);
                break;
            case END:
                receiveMessageOnEndGame(msg);
                break;
            case SINGLEPLAYER:
                receiveMessageOnSinglePlayer(msg);
                break;
        }
    }

    /*public void receiveMessageOnFirstLogin(Message msg) throws IOException{

        VirtualView vv = vvMap.get(msg.getSenderUser());

        if (msg.getMessageType() == Content.LOGIN_DATA) {
            if (firstPlayer.compareAndSet(true, false)) {
                table.addObserver(vv);
                System.out.println(((LoginData) msg).getNickname() + " has joined");
                table.addPlayer(((LoginData) msg).getNickname());
                vv.fetchPlayersNumber();
            }
            setTableState(TableState.WAITING);
        }
    }

    public void receiveMessageOnLogin(Message msg) throws IOException {

        VirtualView vv = vvMap.get(msg.getSenderUser());

        switch (msg.getMessageType()) {

            case PLAYERS_NUMBER:
                if (((PlayersNumber) msg).getNum() == 1){
                    vv.displayGenericMessage("You chose Single Player Mode");
                    setTableState(TableState.SINGLEPLAYER);
                    break;
                }
                if (((PlayersNumber) msg).getNum() < 2 || ((PlayersNumber) msg).getNum() > 4) {
                    vv.fetchPlayersNumber();
                }
                table.setNumPlayers(((PlayersNumber) msg).getNum());
                if (verifyNumPlayers()){
                    startGame();
                }
                else{
                    vv.displayGenericMessage("Please wait for other players to join...");
                }
                break;

            case LOGIN_DATA:
                Player existingNickname = table.getPlayers().stream()
                        .filter(player -> player.getNickname().equals(((LoginData) msg).getNickname()))
                        .findFirst().orElse(null);
                if (existingNickname==null){
                    table.addObserver(vv);
                    System.out.println(((LoginData) msg).getNickname() + " has joined");
                    table.addPlayer(((LoginData) msg).getNickname());
                    if (verifyNumPlayers()) startGame();
                    else vv.displayGenericMessage("Please wait for other players to join...");
                }
                else {
                    vv.displayGenericMessage("This nickname is already taken by another player...\nTry again!");
                    vv.fetchNickname();
                }
                break;

        }
    }
   */
    public void receiveMessageOnSetup(Message msg) throws IOException {
        VirtualView vv = vvMap.get(msg.getSenderUser());
        Resource resourceChosen = new Resource(1, ResourceType.WHITERESOURCE);
        switch (msg.getMessageType()){
            case RESOURCE_TYPE:
                vv.displayGenericMessage("You chose: " + ((ResourceTypeChosen)msg).toString());
                resourceChosen.setType(((ResourceTypeChosen)msg).getResourceType());
                vv.fetchResourcePlacement();
                break;
            case RESOURCE_PLACEMENT:
                for (Player player : table.getPlayers())
                if (msg.getSenderUser().equals(player.getNickname()))
                    player.getPersonalBoard().getWarehouse().getDepot().addResourceToDepot(resourceChosen, ((ResourcePlacement)msg).getFloor());
                break;
        }


    }

    public void startGame() throws IOException {
        for (String key: vvMap.keySet()){
            vvMap.get(key).displayGenericMessage("All the players joined the game.\n Game is loading...\n");
        }
        setTableState(TableState.SETUP);
        setUpGame();
    }

    public void setUpGame() throws IOException {
        giveInitialBonus();
    }

    public void giveInitialBonus() throws IOException {
        table.setPlayersInGame();
        vvMap.get(table.getPlayers().get(1).getNickname()).fetchResourceType();

        if (table.getNumPlayers()>2){
            vvMap.get(table.getPlayers().get(2).getNickname()).fetchResourceType();
            table.getPlayers().get(2).getPersonalBoard().getFaithTrack().moveForward(1);
            vvMap.get(table.getPlayers().get(2).getNickname()).displayGenericMessage("You also moved on faithTrack\n");
        }
        if (table.getNumPlayers()>3){
            vvMap.get(table.getPlayers().get(3).getNickname()).fetchResourceType();
            table.getPlayers().get(3).getPersonalBoard().getFaithTrack().moveForward(1);
            vvMap.get(table.getPlayers().get(3).getNickname()).displayGenericMessage("You moved on faithTrack\n");
            vvMap.get(table.getPlayers().get(3).getNickname()).fetchResourceType();
        }
    }

    public void receiveMessageInGame(Message msg) {
    }

    public void receiveMessageOnEndGame(Message msg) {
    }

    private void receiveMessageOnSinglePlayer(Message msg) {

    }


    /**
     * verifies the number of players to start the game
     * @return true if the number is correct, false otherwise
     */
    public boolean verifyNumPlayers() {
        return table.getNumPlayers() == table.getPlayers().size();
    }


}

