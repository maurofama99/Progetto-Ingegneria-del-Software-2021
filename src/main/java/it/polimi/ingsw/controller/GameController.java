package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagescs.LoginData;

import java.util.ArrayList;

//partita con minimo 2 giocatori
//
public class GameController {
    private static final int MIN_OF_PLAYERS = 2;
    private static final int MAX_OF_PLAYERS = 4;
    private PlayerController playerController;
    private ArrayList<Player> playersInGame;
    private Player activePlayer;

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

        switch (msg.getMessageType()){
            case LOGIN_DATA:
                System.out.println(((LoginData) msg).getNickname() + " has joined");


        }
    }

    public void sendMessage(Message msg){

    }


}
