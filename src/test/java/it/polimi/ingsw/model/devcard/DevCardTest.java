package it.polimi.ingsw.model.devcard;

import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.warehouse.*;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class DevCardTest {
    Depot depot;
    StrongBox strongBox;
    Warehouse warehouse;
    PersonalBoard personalBoard;
    DevCard devCard;

    @Before
    public void setUp(){
        depot = new Depot();
        strongBox = new StrongBox();
        warehouse = new Warehouse(depot, strongBox);
        personalBoard = new PersonalBoard(warehouse);
    }

    @Test
    public void testBuyDevCards() throws CloneNotSupportedException, IllegalAccessException {

        Player p1 = new Player("Vale");
        p1.setPersonalBoard(personalBoard);

        devCard = createDevelopmentCards(1);
        boolean check = false;

        p1.buyDevCard(devCard, devCard.getRequirementsDevCard(), 1);

       // todo con try catch assertFalse(p1.buyDevCard(devCard, devCard.getRequirementsDevCard(), 1));


        //add 3 stone to depot
        Resource threeStones = new Resource(3, ResourceType.STONE);
        p1.getPersonalBoard().getWarehouse().getDepot().addResourceToDepot(threeStones,3);
        assertTrue(p1.getPersonalBoard().getWarehouse().getDepot().getFloors().get(2).isPresent());
        assertEquals(3, p1.getPersonalBoard().getWarehouse().getDepot().getFloors().get(2).get().getQnt());
        assertEquals(ResourceType.STONE, p1.getPersonalBoard().getWarehouse().getDepot().getFloors().get(2).get().getType());

        // add 1 stone 1 coin to strongbox
        ArrayList<Resource> onestoneonecoin = new ArrayList<>();
        onestoneonecoin.add(new Resource(2, ResourceType.STONE));
        onestoneonecoin.add(new Resource(1, ResourceType.COIN));
        p1.getPersonalBoard().getWarehouse().getStrongBox().addResourceToStrongBox(onestoneonecoin);
        assertEquals(1, p1.getPersonalBoard().getWarehouse().getStrongBox().getStoredResources()[0].getQnt());
        assertEquals(2, p1.getPersonalBoard().getWarehouse().getStrongBox().getStoredResources()[3].getQnt());

        //add 1 to coin floors
        Resource onecoin = new Resource(1, ResourceType.COIN);
        p1.getPersonalBoard().getWarehouse().getDepot().addResourceToDepot(onecoin,1);
        assertTrue(p1.getPersonalBoard().getWarehouse().getDepot().getFloors().get(0).isPresent());
        assertEquals(1, p1.getPersonalBoard().getWarehouse().getDepot().getFloors().get(0).get().getQnt());
        assertEquals(ResourceType.COIN, p1.getPersonalBoard().getWarehouse().getDepot().getFloors().get(0).get().getType());

        //buy dC
        devCard = createDevelopmentCards(1);
        assertTrue(devCard.checkRequirements(devCard.getRequirementsDevCard(), p1));
        p1.getPersonalBoard().getSlots()[0].placeDevCard(devCard);
        assertEquals(2, p1.getPersonalBoard().getWarehouse().getStrongBox().getStoredResources()[3].getQnt());
        assertEquals(1, p1.getPersonalBoard().getWarehouse().getDepot().getFloors().get(2).get().getQnt());

        //activate production
        p1.activateProd(0);
        assertTrue(p1.getPersonalBoard().getWarehouse().getDepot().getFloors().get(2).isEmpty());
        assertEquals(1, p1.getPersonalBoard().getWarehouse().getStrongBox().getStoredResources()[3].getQnt());
    }


    public DevCard createDevelopmentCards (int level){
        ArrayList<Resource> requirements;
        requirements = new ArrayList<>();
        requirements.add(new Resource(2,ResourceType.STONE));

        ArrayList<Resource> input= new ArrayList<>();
        input.add(new Resource(2, ResourceType.STONE));
        input.add(new Resource(2,ResourceType.COIN));
        ArrayList<Resource> output= new ArrayList<>();
        output.add(new Resource(3, ResourceType.SHIELD));
        Production prod = new Production(input,output);

        return new DevCard(level, Color.GREEN, 3, requirements, prod);

    }


}
