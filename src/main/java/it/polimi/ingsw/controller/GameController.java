package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.network.messagescs.PlayersNumber;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.observerPattern.Observer;
import it.polimi.ingsw.view.VirtualView;
import java.util.concurrent.atomic.AtomicBoolean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

//partita con minimo 2 giocatori
//
public class GameController implements Observer {
    private Player activePlayer;
    private ClientHandler clientHandler;
    private Table table;
    private TableState tableState = TableState.WAITING_FOR_FIRSTPLAYER;
    private VirtualView virtualView;
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

    public void setUpGame() {
        //playerState è in start --> chiedere al client quale risorsa vuole aggiungere
        //assignInitialBonus(table.getPlayers());

        setTableState(TableState.IN_GAME);
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
        }
    }

    public void receiveMessageOnFirstLogin(Message msg) throws IOException{

        VirtualView vv = vvMap.get(msg.getSenderUser());

        switch (msg.getMessageType()){
            case LOGIN_DATA:
                table.addObserver(vv);
                System.out.println(((LoginData) msg).getNickname() + " has joined");
                table.addPlayer(((LoginData) msg).getNickname());
                if(firstPlayer.compareAndSet(true, false)) {
                    vv.fetchPlayersNumber();
                }
                else {
                    vv.displayGenericMessage("Please wait for other Players");
                    if (verifyNumPlayers()) startGame();//due client entrano in questo case
                }
                break;


            case PLAYERS_NUMBER:
                //TODO: SINGLEPLAYER?
                if (((PlayersNumber) msg).getNum() < 2 || ((PlayersNumber) msg).getNum() > 4) {
                    vv.fetchPlayersNumber();
                    break;
                }
                table.setNumPlayers(((PlayersNumber) msg).getNum());
                setTableState(TableState.WAITING);
                vv.displayGenericMessage("Please wait for other Players");
                break;
        }
    }
    public void receiveMessageOnLogin(Message msg) throws IOException {

        VirtualView vv = vvMap.get(msg.getSenderUser());

        switch (msg.getMessageType()) {

            case LOGIN_DATA:
                table.addObserver(vv);
                for (Player player : table.getPlayers()) {
                    if (player.getNickname().equals(((LoginData) msg).getNickname())) {
                        vv.fetchNickname();
                        break;
                    }
                }

                System.out.println("entri qui scemo?");
                System.out.println(((LoginData) msg).getNickname() + " has joined");
                table.addPlayer(((LoginData) msg).getNickname());
                vv.displayGenericMessage("Please wait for other Players");
                if (verifyNumPlayers()) startGame();
                break;

        }
    }

    public void receiveMessageOnSetup(Message msg) {

    }

    public void startGame() throws IOException {
        //vanno selezionate tutte le view e poi chiami displayGenericMessage("Game can start to be setup!");
        setTableState(TableState.SETUP);
    }

    public void giveInitialBonus(String nickname) {

    }


    public void receiveMessageInGame(Message msg) {
    }

    public void receiveMessageOnEndGame(Message msg) {
    }


    //verifica se il numero d giocatori è corretto per iniziare la partita
    public boolean verifyNumPlayers() {
        return table.getNumPlayers() == table.getPlayers().size();
    }



    //riceve un update dal model, tipo da table??
    //
    @Override
    public void update(Message message) throws IOException {
        //tramite la virtual view manda messaggi in base al tipo di messaggio
        //tipo chiedere nickname
        //virtualView.fetchNickname
    }

}


//todo: inizializza nel server(da costruttore in server app) la vv, a cui dopo passi l'handler una volta che è stato creato