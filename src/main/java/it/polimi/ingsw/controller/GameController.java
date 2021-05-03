package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.network.messagescs.PlayersNumber;
import it.polimi.ingsw.view.VirtualView;
import java.util.concurrent.atomic.AtomicBoolean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

//partita con minimo 2 giocatori

public class GameController{
    private Player activePlayer;

    private Table table;
    private TableState tableState = TableState.WAITING_FOR_FIRSTPLAYER;

    private HashMap<String, VirtualView> vvMap = new HashMap<>();
    private AtomicBoolean firstPlayer = new AtomicBoolean(true);

    public HashMap<String, VirtualView> getVvMap() {
        return vvMap;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setTableState(TableState tableState) {
        this.tableState = tableState;
    }

    public void receiveMessage(Message msg) throws IOException {

        switch (tableState) {
            case WAITING_FOR_FIRSTPLAYER:
                receiveMessageOnFirstLogin(msg);
                break;
            case WAITING:
                receiveMessageOnLogin(msg);
                break;
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

    public void receiveMessageOnFirstLogin(Message msg) throws IOException{

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
                    vv.displayGenericMessage("You choose Single Player Mode");
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

    public void receiveMessageOnSetup(Message msg) {

    }

    public void startGame() throws IOException {
        for (String key: vvMap.keySet()){
            vvMap.get(key).displayGenericMessage("All the players joined the game.\n Game is loading...");
        }
        setTableState(TableState.SETUP);
    }

    public void setUpGame() {
        table.setPlayersInGame(); //this method sets the turn of each player randomly







        setTableState(TableState.IN_GAME);
    }

    public void giveInitialBonus(String nickname) {

    }

    public void receiveMessageInGame(Message msg) {
    }

    public void receiveMessageOnEndGame(Message msg) {
    }

    private void receiveMessageOnSinglePlayer(Message msg) {

    }


    //verifica se il numero di giocatori è corretto per iniziare la partita
    public boolean verifyNumPlayers() {
        return table.getNumPlayers() == table.getPlayers().size();
    }


}

