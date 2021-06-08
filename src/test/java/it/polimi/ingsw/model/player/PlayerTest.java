package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.PlayerController;
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
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class PlayerTest{
    Depot depot;
    StrongBox strongBox;
    Warehouse warehouse;
    PersonalBoard personalBoard;

    ArrayList<LeaderCard> leaderCards;
    Player player;

    ArrayList<Resource> requirements;
    ArrayList<Resource> input;
    ArrayList<Resource> output;
    Production production;

    @Before
    public void setUp(){
        depot = new Depot();
        strongBox = new StrongBox();
        warehouse = new Warehouse(depot, strongBox);
        personalBoard = new PersonalBoard(warehouse);
        leaderCards = new ArrayList<>();

        player = new Player("Pippo");
        player.setPersonalBoard(personalBoard);

        requirements = new ArrayList<>();
        requirements.add(new Resource(2, ResourceType.STONE));
        requirements.add(new Resource(3, ResourceType.COIN));

        input= new ArrayList<>();
        input.add(new Resource(1, ResourceType.SHIELD));

        output= new ArrayList<>();
        output.add(new Resource(5, ResourceType.STONE));
        output.add(new Resource(5,ResourceType.COIN));

        production = new Production("test",input,output);

        depot.addResourceToDepot(new Resource(1, ResourceType.SHIELD), 1);
        depot.addResourceToDepot(new Resource(2, ResourceType.STONE), 2);
        depot.addResourceToDepot(new Resource(3, ResourceType.COIN), 3);
    }


    @Test
    public void testBuyDevCard() throws IllegalAccessException{

        DevCard dCard1 = new DevCard(1, Color.GREEN, 3, requirements,production);
        DevCard dCard2 = new DevCard(2, Color.GREEN, 5, requirements, production);
        DevCard dCard3 = new DevCard(3, Color.BLUE, 1, requirements, production);


        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        resourcesToAdd.add(new Resource(20, ResourceType.STONE));
        resourcesToAdd.add(new Resource(10, ResourceType.COIN));
        player.getPersonalBoard().getWarehouse().getStrongBox().addResourceToStrongBox(resourcesToAdd);

        //buy dCard1 and dCard2:total cost 4 stones
        player.buyDevCard(dCard1, dCard1.getRequirementsDevCard(), 1);
        player.buyDevCard(dCard2,dCard2.getRequirementsDevCard(), 1);
        assertTrue(player.getPersonalBoard().getSlots()[0].getCards().contains(dCard1) &&
                player.getPersonalBoard().getSlots()[0].getCards().contains(dCard2));


        boolean thrown = false;
        try {
            player.buyDevCard(dCard2,dCard2.getRequirementsDevCard(), 3);
        } catch (IllegalAccessException e ) {
            thrown = true;
        }
        assertTrue(thrown);

    }

    @Test
    public void testActivateDiscardLeader() throws IllegalAccessException {

        ArrayList<Color> colorsDevCards = new ArrayList<>();
        colorsDevCards.add(Color.GREEN);

        LeaderCard lCard1 = new LeaderCard(2, new AddProduction(Color.GREEN, new Resource(1, ResourceType.STONE)));
        LeaderCard lCard2 = new LeaderCard(1, new Discount(ResourceType.COIN, colorsDevCards));
        LeaderCard lCard3 = new LeaderCard(3, new SwapWhite(colorsDevCards, new Resource(1, ResourceType.COIN)));
        LeaderCard lCard4 = new LeaderCard(4, new ExtraDepot(ResourceType.COIN, ResourceType.STONE));

        player.getLeaderCards().add(lCard1);
        player.getLeaderCards().add(lCard2);
        player.getLeaderCards().add(lCard3);
        player.getLeaderCards().add(lCard4);

        //player discards two leader cards
        player.discardLeader(2, 3);
        assertTrue(player.getLeaderCards().contains(lCard1) && player.getLeaderCards().contains(lCard2)
                && !player.getLeaderCards().contains(lCard3) && !player.getLeaderCards().contains(lCard4));

        player.buyDevCard(new DevCard(1, Color.GREEN, 3, requirements, production), requirements, 1);
        depot.addResourceToDepot(new Resource(2, ResourceType.STONE), 2);
        depot.addResourceToDepot(new Resource(3, ResourceType.COIN), 3);
        player.buyDevCard(new DevCard(2, Color.GREEN, 3, requirements, production), requirements,  1);
        depot.addResourceToDepot(new Resource(2, ResourceType.STONE), 2);
        depot.addResourceToDepot(new Resource(3, ResourceType.COIN), 3);


        player.activateLeaderCard(0);
        player.activateLeaderCard(0);
        assertTrue(player.getPersonalBoard().getActiveLeaderCards().contains(lCard1)
                && player.getPersonalBoard().getActiveLeaderCards().contains(lCard2));

        assertTrue(personalBoard.hasEffect(EffectType.ADDPRODUCTION) &&
                personalBoard.hasEffect(EffectType.DISCOUNT));

        assertFalse(personalBoard.hasEffect(EffectType.EXTRADEPOT)
                || personalBoard.hasEffect(EffectType.SWAPWHITE));

    }

    @Test
    public void testActivateProduction() throws IllegalAccessException, CloneNotSupportedException {
        player.buyDevCard(new DevCard(1, Color.GREEN, 3, requirements, production),requirements, 1);

        strongBox.addResourceToStrongBox(player.activateProd(0));

        assertEquals(5, personalBoard.getWarehouse().getStrongBox().getStoredResources()[0].getQnt());
        assertEquals(5, personalBoard.getWarehouse().getStrongBox().getStoredResources()[3].getQnt());

        boolean thrown = false;
        try {
            player.activateProd(2);
        } catch (NoSuchElementException e ) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            player.activateProd(0);
        } catch (NoSuchElementException e ) {
            thrown = true;
        }
        assertTrue(thrown);

        Resource oneShield = player.basicProduction(ResourceType.COIN, ResourceType.STONE, ResourceType.SHIELD);
        ArrayList<Resource> resourceToStrongbox = new ArrayList<>();
        resourceToStrongbox.add(oneShield);
        personalBoard.getWarehouse().getStrongBox().addResourceToStrongBox(resourceToStrongbox);
        assertEquals(1, personalBoard.getWarehouse().getStrongBox().getStoredResources()[2].getQnt());

        thrown = false;
        try {
            player.basicProduction(ResourceType.SHIELD, ResourceType.SERVANT, ResourceType.SHIELD);
        } catch (NoSuchElementException e ) {
            thrown = true;
        }


        assertTrue(thrown);


    }
}