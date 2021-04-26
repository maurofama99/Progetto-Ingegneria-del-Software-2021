package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagescs.LoginData;

import java.util.ArrayList;

//partita con minimo 2 giocatori
public class GameController {
    private static final int MIN_OF_PLAYERS = 2;
    private static final int MAX_OF_PLAYERS = 4;
    private PlayerController playerController;
    private ArrayList<Player> playersInGame;
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
        //chiedere al client quale risorsa vuole aggiungere
        //assignInitialBonus(table.getPlayers());

        setTableState(TableState.IN_GAME);
    }

    //metodo addInitialBonus ma come faccio a sapere quanti giocatori?

    public void receiveMessage(Message msg){

        //a seconda dello stato in cui siamo (switch), richiama il metodo specifico per ogni stato che riceve il messaggio
        switch (msg.getMessageType()){
            case LOGIN_DATA:
                System.out.println(((LoginData) msg).getNickname() + " has joined");
        }
    }

    public void sendMessage(Message msg){

    }


}
