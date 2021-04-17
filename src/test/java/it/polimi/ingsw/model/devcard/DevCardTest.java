package it.polimi.ingsw.model.devcard;

import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.warehouse.*;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

public class DevCardTest {


    @Test
    public void Test1() throws IllegalAccessException{
        Depot depot = new Depot();
        StrongBox sB = new StrongBox();
        Warehouse wH = new Warehouse(depot, sB);
        PersonalBoard pB = new PersonalBoard(wH);   //creo il tavolo di gioco

        DevCard dC = new DevCard();
        ArrayList<Resource> input= new ArrayList<>();
        input.add(new Resource(1, ResourceType.STONE));
        input.add(new Resource(2,ResourceType.COIN));
        ArrayList<Resource> output= new ArrayList<>();
        output.add(new Resource(3, ResourceType.SHIELD));
        Production prod = new Production("prova",input,output);
        dC.setProduction(prod);// dC input: 1 stone, 2 coin
                               // dC output: 3 shield

        ArrayList<Resource> requirements;
        requirements = new ArrayList<>();
        requirements.add(new Resource(2,ResourceType.STONE));
        dC.setRequirementsDevCard(requirements);//dC requirements: 2 stone

        Player p1;
        p1 = new Player("Vale");
        p1.setPersonalBoard(pB); //assegno plancia al giocatore


        //add 3 stone to depot
        Resource threestones = new Resource(3, ResourceType.STONE);
        p1.getPersonalBoard().getWarehouse().getDepot().addResourceToDepot(threestones,3);
        assertTrue(p1.getPersonalBoard().getWarehouse().getDepot().getFloors().get(2).isPresent());
        //assertEquals(threestones, p1.getPersonalBoard().getWarehouse().getDepot().getFloors().get(2).get());

        // add 1 stone 1 coin to strongbox
        ArrayList<Resource> onestoneonecoin = new ArrayList<>();
        onestoneonecoin.add(new Resource(1, ResourceType.STONE));
        onestoneonecoin.add(new Resource(1, ResourceType.COIN));
        p1.getPersonalBoard().getWarehouse().getStrongBox().addResourceToStrongBox(onestoneonecoin);
        Resource[] res2 = new Resource[4];
        res2[0] = new Resource(1, ResourceType.COIN);
        res2[1] = new Resource(0,ResourceType.SERVANT);
        res2[2] = new Resource(0, ResourceType.SHIELD);
        res2[3] = new Resource(1, ResourceType.STONE);
        //assertArrayEquals(res2, p1.getPersonalBoard().getWarehouse().getStrongBox().getStoredResources());

        //add 1 to coin floors
        Resource onecoin = new Resource(1, ResourceType.COIN);
        p1.getPersonalBoard().getWarehouse().getDepot().addResourceToDepot(onecoin,1);
        assertTrue(p1.getPersonalBoard().getWarehouse().getDepot().getFloors().get(1).isPresent());
        assertEquals(onecoin, p1.getPersonalBoard().getWarehouse().getDepot().getFloors().get(1).get());

        //buy dC
        assertTrue(dC.checkRequirements(p1));
        p1.getPersonalBoard().getWarehouse().removeResources(requirements);
        p1.getPersonalBoard().getSlots()[0].placeDevCard(dC);

        //activate production
        assertFalse(dC.getProduction().checkInputResource(p1));
        p1.getPersonalBoard().getWarehouse().removeResources(p1.getPersonalBoard().getSlots()[0].getShowedCard().getProduction().getInput());
        p1.getPersonalBoard().getWarehouse().getStrongBox().addResourceToStrongBox(p1.getPersonalBoard().getSlots()[0].getShowedCard().getProduction().getOutput());
    }


}
