package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.devcard.Color;
import it.polimi.ingsw.model.devcard.DevCard;
import it.polimi.ingsw.model.devcard.Production;
import it.polimi.ingsw.model.player.leadercards.*;
import it.polimi.ingsw.model.player.warehouse.Depot;
import it.polimi.ingsw.model.player.warehouse.StrongBox;
import it.polimi.ingsw.model.player.warehouse.Warehouse;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest{

    @Test
    public void testLeaderCards() throws IllegalAccessException {

        Depot depot = new Depot();
        StrongBox sB = new StrongBox();
        Warehouse wH = new Warehouse(depot, sB);
        PersonalBoard pB = new PersonalBoard(wH);

        ArrayList<LeaderCard> lCards= new ArrayList<>();

        Player player = new Player("Pippo",pB);

        ArrayList<Resource> requirements;
        requirements = new ArrayList<>();
        requirements.add(new Resource(2, ResourceType.STONE));

        ArrayList<Resource> input= new ArrayList<>();
        input.add(new Resource(2, ResourceType.STONE));
        input.add(new Resource(2,ResourceType.COIN));
        ArrayList<Resource> output= new ArrayList<>();
        output.add(new Resource(3, ResourceType.SHIELD));
        Production prod = new Production("prova",input,output);

        // dC input: 1 stone, 2 coin
        // dC output: 3 shield
        // dC requirements: 2 stone
        DevCard dCard1 = new DevCard(1, Color.GREEN, 3, requirements, prod);
        Resource resourceRequired = new Resource(5, ResourceType.STONE);
        Resource oneCoin = new Resource(1, ResourceType.COIN);

        DevCard dCard2 = new DevCard(2, Color.GREEN, 5, requirements, prod);

        Resource threeCoins = new Resource(3, ResourceType.COIN);
        requirements.add(threeCoins);

        DevCard dCard3 = new DevCard(1, Color.BLUE, 1, requirements, prod);

        ArrayList<Color> colorsDevCards = new ArrayList<>();
        colorsDevCards.add(Color.GREEN);

        ArrayList<Resource> resources= new ArrayList<>();
        resources.add(resourceRequired);

        LeaderEffect lC1Effect = new AddProduction(dCard2, resourceRequired);
        LeaderEffect lC2Effect = new Discount(oneCoin, colorsDevCards);
        LeaderEffect lC3Effect = new SwapWhite(colorsDevCards, oneCoin);
        LeaderEffect lC4Effect = new ExtraDepot(oneCoin, resources);

        LeaderCard lCard1 = new LeaderCard(2, lC1Effect);
        LeaderCard lCard2 = new LeaderCard(1, lC2Effect);
        LeaderCard lCard3 = new LeaderCard(3, lC3Effect);
        LeaderCard lCard4 = new LeaderCard(4, lC4Effect);


        player.getLeaderCards().add(lCard1);
        player.getLeaderCards().add(lCard2);
        player.getLeaderCards().add(lCard3);
        player.getLeaderCards().add(lCard4);

        player.discardLeader(lCard3, lCard4);
        assertTrue(player.getLeaderCards().contains(lCard1) && player.getLeaderCards().contains(lCard2)
        && !player.getLeaderCards().contains(lCard3) && !player.getLeaderCards().contains(lCard4));



        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        Resource res = new Resource(20, ResourceType.STONE);
        resourcesToAdd.add(res);
        resourcesToAdd.add(threeCoins);
        player.getPersonalBoard().getWarehouse().getStrongBox().addResourceToStrongBox(resourcesToAdd);

        player.buyDevCard(dCard1, 0);
        player.buyDevCard(dCard2, 0);
        assertTrue(player.getPersonalBoard().getSlots()[0].getCards().contains(dCard1) && player.getPersonalBoard().getSlots()[0].getCards().contains(dCard2));

        player.activateLeaderCard(lCard1);
        player.activateLeaderCard(lCard2);
        assertTrue(player.getPersonalBoard().getActiveLeaderCards().contains(lCard1) && player.getPersonalBoard().getActiveLeaderCards().contains(lCard2));

        assertTrue(player.getPersonalBoard().hasEffect(EffectType.DISCOUNT));
        player.buyDevCard(dCard3, 1);
        //assertEquals(1, player.getPersonalBoard().getWarehouse().getStrongBox().getStoredResources()[0].getQnt());










    }
}