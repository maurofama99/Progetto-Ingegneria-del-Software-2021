package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.network.messagescs.PlayersNumber;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.observerPattern.Observer;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

//partita con minimo 2 giocatori
//
public class GameController implements Observer {
    private Player activePlayer;
    private ClientHandler clientHandler;
    private Table table;
    private TableState tableState = TableState.WAITING;
    private VirtualView virtualView;
    private HashMap<String, VirtualView> vvMap = new HashMap<>();

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

    public void receiveMessage(Message msg) throws IOException {

        switch (tableState) {
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

    public void receiveMessageOnLogin(Message msg) throws IOException {

        VirtualView vv = vvMap.get(msg.getSenderUser());

        switch (msg.getMessageType()) {

            case LOGIN_DATA:
                table.addObserver(vv);
                if (table.getPlayers().size() == 0) {
                    vv.fetchPlayersNumber();
                    break;
                }
                for (Player player : table.getPlayers()) {
                    if (player.getNickname().equals(((LoginData) msg).getNickname())) {
                        vv.fetchNickname();
                        break;
                    }
                }
                System.out.println(((LoginData) msg).getNickname() + " has joined");
                table.addPlayer(((LoginData) msg).getNickname());
                vv.displayGenericMessage("Please wait for other Players");
                if (verifyNumPlayers()) startGame();
                break;

            case PLAYERS_NUMBER:
                //TODO: SINGLEPLAYER?
                if (((PlayersNumber) msg).getNum() < 2 || ((PlayersNumber) msg).getNum() > 4) {
                    vv.fetchPlayersNumber();
                }
                table.setNumPlayers(((PlayersNumber) msg).getNum());
                vv.displayGenericMessage("Please wait for other Players");
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