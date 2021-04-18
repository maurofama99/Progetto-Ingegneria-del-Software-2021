package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class DepotTest {

    /**
     * Testing method addResourceToDepot and switchFloors
     */
    @Test
    public void testCheckCorrectPlacement() {

        Depot depot = new Depot();
        StrongBox sB = new StrongBox();
        Warehouse wH = new Warehouse(depot, sB);

        Resource oneCoin = new Resource(1, ResourceType.COIN);
        Resource twoCoins = new Resource(2, ResourceType.COIN);
        Resource twoStones = new Resource(2, ResourceType.STONE);
        Resource twoShields = new Resource(2, ResourceType.SHIELD);
        Resource threeServants = new Resource(3, ResourceType.SERVANT);

       depot.addResourceToDepot(oneCoin, 1);
       assertEquals(1, depot.getFloors().get(0).get().getQnt());

        boolean thrown = false;
        try {
            depot.addResourceToDepot(threeServants, 2);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);


        depot.addResourceToDepot(twoShields, 2);
        assertEquals(2, depot.getFloors().get(1).get().getQnt());

        depot.switchFloors(2, 3);
        assertEquals(2, depot.getFloors().get(2).get().getQnt());
        assertEquals(ResourceType.SHIELD, depot.getFloors().get(2).get().getType());

        depot.switchFloors(2, 1);
        assertEquals(1, depot.getFloors().get(1).get().getQnt());
        assertEquals(ResourceType.COIN, depot.getFloors().get(1).get().getType());

        thrown = false;
        try {
            depot.switchFloors(3, 1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);









    }
}




