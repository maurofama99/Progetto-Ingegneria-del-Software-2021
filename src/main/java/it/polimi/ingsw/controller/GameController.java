package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.messagescs.*;
import it.polimi.ingsw.network.messagessc.AskAction;
import it.polimi.ingsw.network.messagessc.GenericMessage;
import it.polimi.ingsw.observerPattern.Observable;
import it.polimi.ingsw.observerPattern.Observer;
import it.polimi.ingsw.view.VirtualView;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class GameController implements Serializable {
    private PlayerController playerController;

    private Table table;
    private TableState tableState = TableState.WAITING_FOR_FIRSTPLAYER;

    private Resource resourceChosen = new Resource(1, ResourceType.WHITERESOURCE);
    private HashMap<String, VirtualView> vvMap = new HashMap<>();
    private boolean condition = false;
    private int playerCounter=0; //to count how many players discarded leadercards

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

    public void receiveMessage(Message msg) throws IOException, IllegalAccessException {

        switch (tableState) {
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

    public void startGame() throws IOException {
        for (String key : vvMap.keySet()) {
            vvMap.get(key).displayGenericMessage("All the players joined the game.\n Game is loading...\n");
        }
        setTableState(TableState.SETUP);
        setUpGame();
    }

    public void receiveMessageOnSetup(Message msg) throws IOException {
        VirtualView vv = vvMap.get(msg.getSenderUser());

        switch (msg.getMessageType()) {

            case RESOURCE_TYPE:
                vv.displayGenericMessage("You chose: " + ((ResourceTypeChosen)msg).getResourceType());
                resourceChosen.setType(((ResourceTypeChosen) msg).getResourceType());
                vv.fetchResourcePlacement();
                break;

            case RESOURCE_PLACEMENT:
                for (Player player : table.getPlayers()) {
                    if (msg.getSenderUser().equals(player.getNickname()))
                        player.getPersonalBoard().getWarehouse().getDepot().addResourceToDepot(resourceChosen, ((ResourcePlacement) msg).getFloor());
                }

                if (condition)
                    table.dealLeaderCards(msg.getSenderUser());

                break;

            case DISCARD_LEADER:
                for (Player player : table.getPlayers()) {
                    if (msg.getSenderUser().equals(player.getNickname())) {
                        player.discardLeader(((DiscardLeader) msg).getLeaderCard1(), ((DiscardLeader) msg).getLeaderCard2());
                        playerCounter++;
                    }
                }

                if (playerCounter == table.getNumPlayers()) {
                    playerCounter=0;
                    setTableState(TableState.IN_GAME);
                    playerController = new PlayerController(this);
                    vvMap.get(table.getCurrentPlayer().getNickname()).fetchPlayerAction();
                }


                break;


        }


    }


    public void setUpGame() throws IOException {
        table.setPlayersInGame();
        for (Player player : table.getPlayers()) {
            //vvMap.get(player.getNickname()).displayPersonalBoard(player.getPersonalBoard());
        }
        giveInitialBonus();

    }

    public void giveInitialBonus() throws IOException {

        vvMap.get(table.getPlayers().get(0).getNickname()).displayGenericMessage("You are the first player! \nYou now own the Inkwell!\n");
        table.dealLeaderCards(table.getPlayers().get(0).getNickname());

        vvMap.get(table.getPlayers().get(1).getNickname()).displayGenericMessage("You are the second Player!\nYou have an initial bonus:\n1)one extra resource\n");
        vvMap.get(table.getPlayers().get(1).getNickname()).fetchResourceType();

        if (table.getNumPlayers() > 2) {
            vvMap.get(table.getPlayers().get(2).getNickname()).displayGenericMessage("You are the third player!\nYou have an initial bonus:\n1) one extra faithPoint \n2)one extra resource\n");
            vvMap.get(table.getPlayers().get(2).getNickname()).fetchResourceType();
            table.getPlayers().get(2).getPersonalBoard().getFaithTrack().moveForward(1);
        }
        if (table.getNumPlayers() > 3) {
            vvMap.get(table.getPlayers().get(3).getNickname()).displayGenericMessage("You are the fourth player!\nYou have an initial bonus:\n1) one extra faithPoint \n2)two extra resources\n");
            vvMap.get(table.getPlayers().get(3).getNickname()).fetchResourceType();
            table.getPlayers().get(3).getPersonalBoard().getFaithTrack().moveForward(1);
            vvMap.get(table.getPlayers().get(3).getNickname()).fetchResourceType();
        }

        condition = true;
    }


    public void receiveMessageInGame(Message msg) throws IOException, IllegalAccessException {
        switch (msg.getMessageType()){
            case GOING_MARKET:
                playerController.setPlayerAction(PlayerAction.GOTO_MARKET);
                playerController.receiveMessage(msg);
                break;
            case ACTIVATE_PRODUCTION:
                playerController.setPlayerAction(PlayerAction.ACTIVATE_PRODUCTION);
                playerController.receiveMessage(msg);
                break;
            case BUY_DEVCARD:
                playerController.setPlayerAction(PlayerAction.BUY_DEVCARD);
                playerController.receiveMessage(msg);
                break;
            case ACTIVATE_LEADER:
                playerController.setPlayerAction(PlayerAction.ACTIVATE_LEADER);
                playerController.receiveMessage(msg);
                break;
            case DONE_ACTION:
                playerController.setPlayerAction(PlayerAction.WAITING);
                playerController.receiveMessage(msg);
                break;
            default:
                playerController.receiveMessage(msg);
                break;
        }
    }

    public void receiveMessageOnEndGame(Message msg) {
    }

    private void receiveMessageOnSinglePlayer(Message msg) {

    }



}

