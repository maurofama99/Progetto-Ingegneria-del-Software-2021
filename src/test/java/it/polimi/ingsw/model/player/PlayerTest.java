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

        Player player = new Player("Pippo");

        player.setPersonalBoard(pB);

        ArrayList<Resource> requirements;
        requirements = new ArrayList<>();
        requirements.add(new Resource(2, ResourceType.STONE));

        ArrayList<Resource> input= new ArrayList<>();
        input.add(new Resource(2, ResourceType.STONE));
        input.add(new Resource(2,ResourceType.COIN));
        ArrayList<Resource> output= new ArrayList<>();
        output.add(new Resource(3, ResourceType.SHIELD));
        Production prod = new Production("prova",input,output);

        // dC1 input: 2 stone, 2 coin
        // dC1 output: 3 shield
        // dC1 requirements: 2 stone
        DevCard dCard1 = new DevCard(1, Color.GREEN, 3, requirements,prod);
        
        // dC2 input: 2 stone, 2 coin
        // dC2 output: 3 shield
        // dC2 requirements: 2 stone
        DevCard dCard2 = new DevCard(2, Color.GREEN, 5, requirements, prod);

        Resource threeCoins = new Resource(3, ResourceType.COIN);
        requirements.add(threeCoins);

        // dC3 input: 2 stone, 2 coin
        // dC3 output: 3 shield
        // dC3 requirements: 2 stone, 3coins
        DevCard dCard3 = new DevCard(3, Color.BLUE, 1, requirements, prod);

        Resource resourceRequired = new Resource(5, ResourceType.STONE);
        Resource oneCoin = new Resource(1, ResourceType.COIN);

        ArrayList<Color> colorsDevCards = new ArrayList<>();
        colorsDevCards.add(Color.GREEN);
        LeaderEffect lC1Effect = new AddProduction(Color.GREEN, resourceRequired);
        LeaderEffect lC2Effect = new Discount(oneCoin.getType(), colorsDevCards);
        LeaderEffect lC3Effect = new SwapWhite(colorsDevCards, oneCoin);
        LeaderEffect lC4Effect = new ExtraDepot(oneCoin.getType(), resourceRequired.getType());

        LeaderCard lCard1 = new LeaderCard(2, lC1Effect); //AddProduction
        LeaderCard lCard2 = new LeaderCard(1, lC2Effect); //Discount
        LeaderCard lCard3 = new LeaderCard(3, lC3Effect); //SwapWhite
        LeaderCard lCard4 = new LeaderCard(4, lC4Effect); //ExtraDepot


        player.getLeaderCards().add(lCard1);
        player.getLeaderCards().add(lCard2);
        player.getLeaderCards().add(lCard3);
        player.getLeaderCards().add(lCard4);

        //player discards two leader cards
        player.discardLeader(2, 3);
        assertTrue(player.getLeaderCards().contains(lCard1) && player.getLeaderCards().contains(lCard2)
        && !player.getLeaderCards().contains(lCard3) && !player.getLeaderCards().contains(lCard4));


        //add 20 stones and 3 coins to strongbox
        Resource res = new Resource(20, ResourceType.STONE);
        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        resourcesToAdd.add(res);
        resourcesToAdd.add(threeCoins);
        player.getPersonalBoard().getWarehouse().getStrongBox().addResourceToStrongBox(resourcesToAdd);

        //buy dCard1 and dCard2:total cost 4 stones
        player.buyDevCard(dCard1, 1);
        player.buyDevCard(dCard2, 1);
        assertTrue(player.getPersonalBoard().getSlots()[0].getCards().contains(dCard1) && player.getPersonalBoard().getSlots()[0].getCards().contains(dCard2));

        //activate two leader cards: addProduction and discount on Coin
        player.activateLeaderCard(lCard1);
        player.activateLeaderCard(lCard2);
        assertTrue(player.getPersonalBoard().getActiveLeaderCards().contains(lCard1) && player.getPersonalBoard().getActiveLeaderCards().contains(lCard2));

        //check discount works
        assertTrue(player.getPersonalBoard().hasEffect(EffectType.DISCOUNT));
        player.buyDevCard(dCard3, 1);
        //todo assertEquals(1, player.getPersonalBoard().getWarehouse().getStrongBox().getStoredResources()[0].getQnt());










    }
}