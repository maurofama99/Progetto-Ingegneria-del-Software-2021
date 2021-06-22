package it.polimi.ingsw.model.player.leadercards;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LeaderCardTest  {

    @Test
    public void swapWhiteTest(){
        GameController gameController = new GameController();
        ArrayList<Resource> resources = new ArrayList<>();
        ArrayList<LeaderCard> activeLeaderCards;
        Table table = new Table();
        Resource newResource;

        resources.add(new Resource(1, ResourceType.STONE));
        resources.add(new Resource(1, ResourceType.STONE));
        resources.add(new Resource(1, ResourceType.WHITERESOURCE));
        resources.add(new Resource(1, ResourceType.FAITHPOINT));

        activeLeaderCards = table.getLeaderCardsDeck();
        System.out.println(activeLeaderCards);

        System.out.println("Original resources: " + resources);

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
    public void sayStuff(){
        Table table = new Table();
        System.out.println("/front/leader_" + table.getLeaderCardsDeck().get(0).getLeaderEffect().toString() + ".png");
    }

}