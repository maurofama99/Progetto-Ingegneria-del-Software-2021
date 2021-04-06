package it.polimi.ingsw.model.devcard;

import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.warehouse.*;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class DevCardTest {


    @Test
    public void Test1() throws IllegalAccessException, CloneNotSupportedException {
        FirstFloor fF = new FirstFloor();
        SecondFloor sF = new SecondFloor();
        ThirdFloor tF = new ThirdFloor();
        StrongBox sB = new StrongBox();
        Warehouse wH = new Warehouse(fF, sF, tF, sB);
        PersonalBoard pB = new PersonalBoard(wH);

        ArrayList<Resource> input;
        input = new ArrayList<>();
        input.add(new Resource(1, ResourceType.STONE));
        input.add(new Resource(2,ResourceType.COIN));
        Production prod = new Production("prova",input,input);

        DevCard dC = new DevCard();
        dC.setProduction(prod);
        ArrayList<Resource> requirements;
        requirements = new ArrayList<>();
        requirements.add(new Resource(2,ResourceType.STONE));
        dC.setRequirementsDevCard(requirements);

        Player p1;
        p1 = new Player("Vale");
        p1.setPersonalBoard(pB);


        //TEST:
        //      controlla se il player ha i requisiti per comprare la devcard dC
        //      si -> piazza la devcard dC nel primo slot della personal board
        //      controlla se il player ha i requisiti per attivare la produzione

        ArrayList<Resource> resources = new ArrayList<>();
        resources.add(new Resource(10, ResourceType.STONE));
        resources.add(new Resource(20, ResourceType.COIN));

        p1.getPersonalBoard().getWarehouse().addResourcesToFloor(resources);
        p1.getPersonalBoard().getWarehouse().getStrongBox().addResourceToStrongBox(resources);
        assertTrue(dC.checkRequirements(p1));
        p1.getPersonalBoard().getWarehouse().removeResources(requirements);
        p1.getPersonalBoard().getSlots()[0].PlaceDevCard(dC);
        assertFalse(dC.getProduction().checkInputResource(p1));
        p1.getPersonalBoard().getWarehouse().removeResources(p1.getPersonalBoard().getSlots()[0].getShowedCard().getProduction().getInput());
        p1.getPersonalBoard().getWarehouse().getStrongBox().addResourceToStrongBox(p1.getPersonalBoard().getSlots()[0].getShowedCard().getProduction().getOutput());
    }


}
