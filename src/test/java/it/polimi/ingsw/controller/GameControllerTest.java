package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.network.messagescs.BuyDevCard;
import it.polimi.ingsw.network.messagescs.GoingMarket;
import it.polimi.ingsw.network.messagescs.LoginData;
import it.polimi.ingsw.network.messagescs.ResourceTypeChosen;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.view.VirtualView;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class GameControllerTest {

    /*
    private GameController gameController;
    private WaitingRoom waitingRoom;
    private PlayerController playerController;
    private HashMap<String, VirtualView> vvMap;
    private ClientHandler clientHandler;


    @Before
    public void setUp() throws IOException {
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
        playerController = new PlayerController(gameController);
    }

    @Test
    public void testSetUpGameController(){
        assertEquals(3, gameController.getTable().getNumPlayers());
        assertEquals(gameController.getTableState(), TableState.SETUP);
        gameController.setTableState(TableState.IN_GAME);
        assertEquals(gameController.getTableState(), TableState.IN_GAME);
    }

    @Test
    public void testChooseResource() throws IOException {
        ResourceTypeChosen resourceTypeChosen = new ResourceTypeChosen("Valeria", 1);
        gameController.receiveMessageOnSetup(resourceTypeChosen);
        assertEquals(ResourceType.SERVANT, gameController.getResourceChosen().getType());
    }


    @Test
    public void testGoToMarket() throws IOException, CloneNotSupportedException {
        GoingMarket goingMarket = new GoingMarket(1, true);
        playerController.receiveMessageOnMarket(goingMarket);


    }

     */

}