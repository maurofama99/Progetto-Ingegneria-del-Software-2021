package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.SinglePlayerController;
import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LeaderCardTest {
    GameController gameController;
    Player player;

    @Before
    public void setUp(){
        player = new Player("Vale");
        gameController = new GameController(new SinglePlayerController(player));
        gameController.setTable(new Table(player));
    }

    @Test
    public void swapWhiteTest(){
        ArrayList<Resource> resources = new ArrayList<>();
        ArrayList<LeaderCard> activeLeaderCards;
        Resource newResource;

        resources.add(new Resource(1, ResourceType.STONE));
        resources.add(new Resource(1, ResourceType.STONE));
        resources.add(new Resource(1, ResourceType.WHITERESOURCE));
        resources.add(new Resource(1, ResourceType.FAITHPOINT));

        activeLeaderCards = gameController.getTable().getLeaderCardsDeck();

        for(LeaderCard leaderCard : activeLeaderCards){
            if (leaderCard.getLeaderEffect().getEffectType().equals(EffectType.SWAPWHITE)){
                newResource = (Resource) leaderCard.getLeaderEffect().getObject();
                for (Resource res : resources){
                    if (res.getType().equals(ResourceType.WHITERESOURCE)){
                        res.setType(newResource.getType());
                    }
                }
            }
        }
    }

    @Test
    public void testExtraDepot(){

    }
}