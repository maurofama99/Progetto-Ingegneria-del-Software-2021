package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.LocalGameManager;
import it.polimi.ingsw.network.messagescs.GoingMarket;
import it.polimi.ingsw.network.messagescs.ResourcePlacement;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.view.VirtualView;
import it.polimi.ingsw.view.cli.Cli;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

import static org.junit.Assert.*;

public class SinglePlayerControllerTest {

    SinglePlayerController spc;
    Player soloPlayer;
    LocalGameManager localGameManager;
    WaitingRoom waitingRoom;
    PlayerController playerController;
    VirtualView vv;
    SinglePlayerTableState singlePlayerTableState;
    ClientHandler clientHandler;
    ObjectOutputStream objectOutputStream;

    @Before
    public void setUp() throws Exception {
        clientHandler = new ClientHandler(waitingRoom, localGameManager);
        soloPlayer = new Player("Gigio");
        spc = new SinglePlayerController(soloPlayer);
        spc.setGameController(new GameController(spc));
        localGameManager = new LocalGameManager(new Client(new Cli()));
        waitingRoom = new WaitingRoom();
        singlePlayerTableState = SinglePlayerTableState.SETUP;
        vv = new VirtualView(clientHandler);
        spc.setVirtualView(vv);
        spc.getGameController().setTable(spc.getTable());
        HashMap<String, VirtualView> result = new HashMap<>();
        result.put(soloPlayer.getNickname(), vv);
        spc.getGameController().setVvMap(result);
        playerController = new PlayerController(spc.getGameController());
        spc.getGameController().setPlayerController(playerController);
        spc.getTable().getSinglePlayer().getPersonalBoard().getFaithTrack().addObserver(spc);
        objectOutputStream = new ObjectOutputStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        });
        clientHandler.setOutput(objectOutputStream);
    }

    @Test
    public void testUpdate() throws IOException {
        soloPlayer.getPersonalBoard().getFaithTrack().setFaithMarkerPosition(7);
        soloPlayer.getPersonalBoard().getFaithTrack().moveForward(soloPlayer, 1);
        assertEquals(2, soloPlayer.getVictoryPoints());
        assertTrue(soloPlayer.getPersonalBoard().getFaithTrack().isFirstFavorTile());
    }

    @Test
    public void testEndGame() throws IOException{
            //test end game when player reaches last faith tile
            soloPlayer.getPersonalBoard().getFaithTrack().setFaithMarkerPosition(23);
            assertEquals(spc.getSinglePlayerTableState(), SinglePlayerTableState.SETUP);
            soloPlayer.getPersonalBoard().getFaithTrack().moveForward(soloPlayer, 1);
            assertEquals(spc.getSinglePlayerTableState(), SinglePlayerTableState.END);

            //test eng game when lorenzo reaches last faith tile
            spc.setSinglePlayerTableState(SinglePlayerTableState.LORENZOS_TURN);
            soloPlayer.getPersonalBoard().getFaithTrack().setBlackCrossPosition(23);
            spc.getTable().getLorenzoIlMagnifico().moveBlackCross(soloPlayer, 1);
            assertEquals(spc.getSinglePlayerTableState(), SinglePlayerTableState.END);

            //test end game when player buys its 7th card
            spc.getTable().getSinglePlayer().setCounterDevCards(7);
            spc.getGameController().getPlayerController().countDevCards();
            assertEquals(spc.getSinglePlayerTableState(), SinglePlayerTableState.END);

    }

    @Test
    public void testMarketAction() throws IOException, IllegalAccessException, CloneNotSupportedException {
            spc.lorenzoTurn();
            assertEquals(spc.getSinglePlayerTableState(), SinglePlayerTableState.PLAYERS_TURN);
            GoingMarket goingMarket = new GoingMarket(1, true);
            spc.receiveSPMessage(goingMarket);
            assertEquals(spc.getGameController().getPlayerController().getPlayerAction(), PlayerAction.GOTO_MARKET);

    }



}