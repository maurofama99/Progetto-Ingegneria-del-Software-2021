package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;
import java.util.Random;

//partita con minimo 2 giocatori
public class GameController {
    private static final int MIN_OF_PLAYERS = 2;
    private static final int MAX_OF_PLAYERS = 4;

    //game server here

    //creare una struttura dati che contiene le virtual view dei player

    private Table table;
    private TableState tableState;

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

    public void receiveMessage(Message msg){

        //a seconda dello stato in cui siamo (switch), richiama il metodo specifico per ogni stato che riceve il messaggio
        System.out.println("Ciao, sono il Game Controller e sono pronto a fare cose");
    }

    public void sendMessage(Message msg){

    }


}
