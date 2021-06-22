package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class WarehouseTest {
    private Depot depot;
    private StrongBox strongBox;
    private Warehouse warehouse;

    private Resource oneCoin;
    private Resource twoStones;
    private Resource twoShields;
    private Resource twoServants;
    private Resource fourStones;


    @Before
    public void setUp(){
        depot = new Depot();
        strongBox = new StrongBox();
        warehouse = new Warehouse(depot, strongBox);
        oneCoin = new Resource(1, ResourceType.COIN);
        twoStones = new Resource(2, ResourceType.STONE);
        twoShields = new Resource(2, ResourceType.SHIELD);
        twoServants = new Resource(2, ResourceType.SERVANT);
        fourStones = new Resource(4, ResourceType.STONE);

    }

    @Test
    public void testCheckAvailabilityWarehouse() throws CloneNotSupportedException {
        depot.addResourceToDepot(oneCoin, 1);
        depot.addResourceToDepot(twoStones, 2);
        depot.addResourceToDepot(twoServants, 3);

        ArrayList<Resource> resourcesToRemove = new ArrayList<>();
        resourcesToRemove.add(fourStones);
        assertFalse(warehouse.checkAvailabilityWarehouse(resourcesToRemove));

        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        resourcesToAdd.add(twoStones);
        strongBox.addResourceToStrongBox(resourcesToAdd);
        assertTrue(warehouse.checkAvailabilityWarehouse(resourcesToRemove));

    }

    /**
     * Testing removeResources using methods of class StrongBox and Depot
     */
    @Test
    public void testRemoveResources() throws CloneNotSupportedException {
        depot.addResourceToDepot(oneCoin, 1);
        depot.addResourceToDepot(twoStones, 2);
        depot.addResourceToDepot(twoServants, 3);
        boolean thrown;

        ArrayList<Resource> resourcesToRemove = new ArrayList<>();
        resourcesToRemove.add(oneCoin);

        warehouse.removeResources(resourcesToRemove);
        assertTrue(depot.getFloors().get(0).isEmpty());


        depot.addResourceToDepot(oneCoin, 1);

        resourcesToRemove.add(fourStones);

        thrown = false;
        try {
            warehouse.removeResources(resourcesToRemove);
        } catch (NoSuchElementException | CloneNotSupportedException e) {
            thrown = true;
        }

        assertTrue(thrown);

        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        resourcesToAdd.add(twoStones);
        resourcesToAdd.add(twoShields);
        strongBox.addResourceToStrongBox(resourcesToAdd);

        resourcesToRemove.remove(oneCoin);
        resourcesToRemove.add(twoShields);

        warehouse.removeResources(resourcesToRemove);
        assertTrue(depot.getFloors().get(1).isEmpty());
        assertEquals(0, strongBox.getStoredResources()[3].getQnt());
        assertEquals(0, strongBox.getStoredResources()[2].getQnt());

    }

    @Test
    public void testSerializableWarehouse(){

        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        resourcesToAdd.add(twoStones);
        resourcesToAdd.add(twoShields);
        strongBox.addResourceToStrongBox(resourcesToAdd);
        warehouse.getDepot().addResourceToDepot(twoServants, 2);

        SerializableWarehouse serializableWarehouse = new SerializableWarehouse(warehouse);

        assertEquals(2, serializableWarehouse.getFloors().get(1).getQnt());
        assertEquals(2, serializableWarehouse.getStrongbox()[2].getQnt());
        assertEquals(ResourceType.STONE, serializableWarehouse.getStrongbox()[3].getType());
    }

    @After
    public void tearDown() {
    }
}