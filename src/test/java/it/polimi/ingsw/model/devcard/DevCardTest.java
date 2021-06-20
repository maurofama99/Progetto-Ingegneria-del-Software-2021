package it.polimi.ingsw.model.devcard;

import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.player.warehouse.*;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Stack;

public class DevCardTest {


    @Test
    public void Test1() throws IllegalAccessException, CloneNotSupportedException {
        Depot depot = new Depot();
        StrongBox sB = new StrongBox();
        Warehouse wH = new Warehouse(depot, sB);
        PersonalBoard pB = new PersonalBoard(wH);   //creo il tavolo di gioco

        ArrayList<Resource> requirements;
        requirements = new ArrayList<>();
        requirements.add(new Resource(2,ResourceType.STONE));

        ArrayList<Resource> input= new ArrayList<>();
        input.add(new Resource(2, ResourceType.STONE));
        input.add(new Resource(2,ResourceType.COIN));
        ArrayList<Resource> output= new ArrayList<>();
        output.add(new Resource(3, ResourceType.SHIELD));
        Production prod = new Production(input,output);

        // dC input: 1 stone, 2 coin
        // dC output: 3 shield
        // dC requirements: 2 stone
        DevCard dC = new DevCard(1, Color.GREEN, 3, requirements, prod);

        ArrayList<LeaderCard> lCards= new ArrayList<>();

        Player p1 = new Player("Vale");

        p1.setPersonalBoard(pB);


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
        assertTrue(dC.checkRequirements(dC.getRequirementsDevCard(), p1));
        p1.getPersonalBoard().getSlots()[0].placeDevCard(dC);
        assertEquals(2, p1.getPersonalBoard().getWarehouse().getStrongBox().getStoredResources()[3].getQnt());
        assertEquals(1, p1.getPersonalBoard().getWarehouse().getDepot().getFloors().get(2).get().getQnt());

        //activate production
        p1.activateProd(0);
        assertTrue(p1.getPersonalBoard().getWarehouse().getDepot().getFloors().get(2).isEmpty());
        assertEquals(1, p1.getPersonalBoard().getWarehouse().getStrongBox().getStoredResources()[3].getQnt());
    }



}
