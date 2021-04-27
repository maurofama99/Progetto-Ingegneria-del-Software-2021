package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.network.messagescs.PlayersNumber;
import it.polimi.ingsw.network.server.ClientHandler;

import java.io.IOException;
import java.util.ArrayList;

//partita con minimo 2 giocatori
//
public class GameController {
    private static final int MIN_OF_PLAYERS = 2;
    private static final int MAX_OF_PLAYERS = 4;
    private PlayerController playerController;
    private ArrayList<Player> playersInGame;
    private Player activePlayer;
    private ClientHandler clientHandler;
    private int numPlayers;

    //creare una struttura dati che contiene le virtual view dei player

    private Table table;
    private TableState tableState = TableState.WAITING;

    public void setTable(Table table) {
        this.table = table;
    }

    public void setTableState(TableState tableState) {
        this.tableState = tableState;
    }

    public void setUpGame(){
        //playerState è in start --> chiedere al client quale risorsa vuole aggiungere
        //assignInitialBonus(table.getPlayers());

        setTableState(TableState.IN_GAME);
    }

    //metodo addInitialBonus ma come faccio a sapere quanti giocatori?
    /*
    public Message addInitialBonus(Player activePlayer){
        switch (playersInGame.indexOf(activePlayer)){
            case 0:
                //salta turno
            case 1:
                //chiedi quale risorsa vuole, poi scarta risorsa leader
            case 2:
                //chiedi quale risorsa vuole e muovi di uno
            case 3:
                //chiedi quale risorsa vuole, chiedi quale altra risorsa vuole, muovi di uno
        }
    }
    */

    /*
    public Message discardInitialLeaderCards(){
     fa vedere le 4 carte e  2 verrano scartate
    }
     */

    public void receiveMessage(Message msg){
        switch (tableState){
            case WAITING:
                receiveMessageOnLogin(msg);
            case SETUP:
                receiveMessageOnSetup(msg);
            case IN_GAME:
                receiveMessageInGame(msg);
            case END:
                receiveMessageOnEndGame(msg);
        }
    }

    private void receiveMessageOnEndGame(Message msg) {
    }

    private void receiveMessageInGame(Message msg) {
    }

    private void receiveMessageOnSetup(Message msg) {
    }


    public void receiveMessageOnLogin(Message msg){

        switch (msg.getMessageType()){
            case LOGIN_DATA:
                if (playersInGame.size() == 0){
                    //manda messaggio chiedendo il numero di player desiderati ask_player_number
                    break;
                }
                for (Player player : playersInGame) {
                    if (player.getNickname().equals(((LoginData) msg).getNickname())) {
                        //deve mandare messaggio login_fail
                        break;
                    }
                }
                System.out.println(((LoginData) msg).getNickname() + " has joined");
                playersInGame.add(new Player(((LoginData) msg).getNickname()));
                //deve mandare messaggio login_succesful
                //chiama metodo che verifica se la partita può iniziare --> manda mex start_game
                break;
            case PLAYERS_NUMBER:
                if (((PlayersNumber) msg).getNum() < 1 || ((PlayersNumber) msg).getNum() > 4){
                    //manda messaggio invalid num players
                    break;
                }
                numPlayers = ((PlayersNumber) msg).getNum();
                //single player??
                //deve mandare messaggio login succesful
        }
    }


    //metodo che dice se array è dimensione max e

    public void sendMessage(Message msg) throws IOException {
            clientHandler.sendMessage(msg);
    }


}


//inizializza nel serer(da costruttore in server app) la vv, a cui dopo passi l'handler una volta che è stato creato