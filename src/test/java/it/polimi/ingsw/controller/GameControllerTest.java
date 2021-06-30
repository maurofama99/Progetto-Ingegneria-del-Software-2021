package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messagescs.GoingMarket;
import it.polimi.ingsw.network.messagescs.ResourcePlacement;
import it.polimi.ingsw.network.messagescs.ResourceTypeChosen;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.VirtualView;
import it.polimi.ingsw.view.cli.Cli;

import java.io.OutputStream;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class GameControllerTest {

    private GameController gameController;
    private WaitingRoom waitingRoom;
    private PlayerController playerController;
    private HashMap<String, VirtualView> vvMap = new HashMap<>();
    private ClientHandler clientHandler;
    private ObjectOutputStream objectOutputStream;


    @Before
    public void setUp() throws IOException {
        clientHandler = new ClientHandler(new Server(waitingRoom, 1269), new Socket(), waitingRoom);
        gameController = new GameController();
        waitingRoom = new WaitingRoom();
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("Valeria"));
        players.add(new Player("Mauro"));
        players.add(new Player("Christian"));
        gameController.setTable(new Table());
        for(Player player : players){
            gameController.getTable().addPlayer(player.getNickname());
        }
        gameController.getTable().setNumPlayers(players.size());
        gameController.getTable().setCurrentPlayer(gameController.getTable().getPlayers().get(0));
        VirtualView vv = new VirtualView(clientHandler);
        vvMap.put(gameController.getTable().getCurrentPlayer().getNickname(), vv);
        gameController.setVvMap(vvMap);
        playerController = new PlayerController(gameController);

        objectOutputStream = new ObjectOutputStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        });
        clientHandler.setOutput(objectOutputStream);
    }

    @Test
    public void testSetUpGameController(){
        assertEquals(3, gameController.getTable().getNumPlayers());
        assertEquals(gameController.getTableState(), TableState.SETUP);
        gameController.setTableState(TableState.IN_GAME);
        assertEquals(gameController.getTableState(), TableState.IN_GAME);
    }

    @Test
    public void testChooseResource() throws IOException, CloneNotSupportedException, IllegalAccessException {
            ResourceTypeChosen resourceTypeChosen = new ResourceTypeChosen("Valeria", 1);
            gameController.receiveMessageOnSetup(resourceTypeChosen);
            assertEquals(ResourceType.SERVANT, gameController.getResourceChosen().getType());
            ResourcePlacement resourcePlacement = new ResourcePlacement("Valeria", "1");
            gameController.receiveMessageOnSetup(resourcePlacement);
            assertEquals(1, gameController.getTable().getCurrentPlayer().getPersonalBoard().getWarehouse().getDepot().getFloors().get(0).get().getQnt());

    }

    @Test
    public void testUpdateOnFaithTrack(){
        for (Player player: gameController.getTable().getPlayers()){
            player.getPersonalBoard().getFaithTrack().addObserver(gameController);
        }
        //test turn favor tile
        gameController.getTable().getPlayers().get(1).getPersonalBoard().getFaithTrack().setFaithMarkerPosition(5);
        gameController.getTable().getPlayers().get(2).getPersonalBoard().getFaithTrack().setFaithMarkerPosition(10);
        gameController.getTable().getCurrentPlayer().getPersonalBoard().getFaithTrack().moveForward(gameController.getTable().getCurrentPlayer(), 8);
        assertTrue(gameController.getTable().getPlayers().get(0).getPersonalBoard().getFaithTrack().isFirstFavorTile());
        assertTrue(gameController.getTable().getPlayers().get(1).getPersonalBoard().getFaithTrack().isFirstFavorTile());
        assertFalse(gameController.getTable().getPlayers().get(2).getPersonalBoard().getFaithTrack().isFirstFavorTile());

        //test end game
        gameController.getTable().getCurrentPlayer().getPersonalBoard().getFaithTrack().setFaithMarkerPosition(23);
        gameController.getTable().getCurrentPlayer().getPersonalBoard().getFaithTrack().moveForward(gameController.getTable().getCurrentPlayer(), 1);
        assertEquals(gameController.getTableState(), TableState.END);

    }

    @Test
    public void testFinalizeProduction(){

    }

    @Test
    public void testHasTwoLeaderCards(){

    }

    @Test
    public void testEndGameDevCard(){

    }

    @Test
    public void hasDiscountEffect(){

    }

    @Test
    public void addFaithPointsToOpponent(){}





}