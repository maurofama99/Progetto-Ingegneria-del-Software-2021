package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.Content;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.LocalGameManager;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.view.VirtualView;
import it.polimi.ingsw.view.cli.Cli;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class WaitingRoomTest {
    WaitingRoom waitingRoom;
    ArrayList<Player> singlePlayerArray;
    ArrayList<Player> twoPlayersArray;
    ArrayList<Player> threePlayersArray;


    @Before
    public void setUp() throws Exception {
        waitingRoom = new WaitingRoom();
        singlePlayerArray = new ArrayList<>();
        twoPlayersArray = new ArrayList<>();
        threePlayersArray = new ArrayList<>();
        waitingRoom.playerInWait("Vale", 3);
        waitingRoom.getPlayerClientHandlerHashMap().put("Vale", new ClientHandler(waitingRoom,new LocalGameManager(new Client(new Cli()))));
        waitingRoom.playerInWait("Mauro", 3);
        waitingRoom.getPlayerClientHandlerHashMap().put("Mauro", new ClientHandler(waitingRoom,new LocalGameManager(new Client(new Cli()))));
        waitingRoom.playerInWait("Christian", 2);
        waitingRoom.getPlayerClientHandlerHashMap().put("Christian", new ClientHandler(waitingRoom,new LocalGameManager(new Client(new Cli()))));


    }

    @Test
    public void setupSingleController() throws IOException {
        assertFalse(waitingRoom.checkGameStart());
        waitingRoom.playerInWait("Vale", 1);
        assertEquals(1, waitingRoom.getSinglePlayerArray().size());
    }

    @Test
    public void setupGameController() throws IOException {
        assertFalse(waitingRoom.checkGameStart());
        waitingRoom.playerInWait("Vale", 2);
        waitingRoom.playerInWait("Christian", 3);
        assertEquals(2, waitingRoom.getTwoPlayersArray().size());
        assertEquals(3, waitingRoom.getThreePlayersArray().size());
    }

    @Test
    public void nicknameAlreadyPresent() {
        assertTrue(waitingRoom.nicknameAlreadyPresent("Vale"));
        assertTrue(waitingRoom.nicknameAlreadyPresent("Christian"));
    }
}