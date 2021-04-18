package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class WarehouseTest {

    /**
     * Testing removeResources using methods of class strongBox and Depot
     */
    @Test
    public void Test() {
        Depot depot = new Depot();
        StrongBox sB = new StrongBox();
        Warehouse wH = new Warehouse(depot, sB);
        boolean thrown;

        Resource oneCoin = new Resource(1, ResourceType.COIN);
        Resource twoCoins = new Resource(2, ResourceType.COIN);
        Resource twoStones = new Resource(2, ResourceType.STONE);
        Resource twoShields = new Resource(2, ResourceType.SHIELD);
        Resource twoServants = new Resource(2, ResourceType.SERVANT);
        Resource fourStones = new Resource(4, ResourceType.STONE);

        depot.addResourceToDepot(oneCoin, 1);
        depot.addResourceToDepot(twoStones, 2);
        depot.addResourceToDepot(twoServants, 3);

        ArrayList<Resource> resourcesToRemove = new ArrayList<>();
        resourcesToRemove.add(twoCoins);

        thrown = false;
        try {
            wH.removeResources(resourcesToRemove);
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        assertTrue(thrown);

        resourcesToRemove.remove(twoCoins);
        resourcesToRemove.add(oneCoin);

        wH.removeResources(resourcesToRemove);
        assertTrue(depot.getFloors().get(0).isEmpty());

        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        resourcesToAdd.add(twoStones);
        resourcesToAdd.add(twoShields);
        sB.addResourceToStrongBox(resourcesToAdd);

        resourcesToRemove.remove(oneCoin);
        resourcesToRemove.add(fourStones);
        resourcesToRemove.add(twoShields);

        wH.removeResources(resourcesToRemove);
        assertTrue(depot.getFloors().get(1).isEmpty());
        assertEquals(0, sB.getStoredResources()[3].getQnt());
        assertEquals(0, sB.getStoredResources()[2].getQnt());

    }

}