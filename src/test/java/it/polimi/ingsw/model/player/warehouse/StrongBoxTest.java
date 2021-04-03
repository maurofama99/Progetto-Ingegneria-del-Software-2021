package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.model.player.warehouse.StrongBox;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class StrongBoxTest {

    private StrongBox strongBox;

    @Before
    public void setUp(){
        Resource[] storedResources = new Resource[4];
        strongBox = new StrongBox(storedResources);
        storedResources[0] = new Resource(2, ResourceType.COIN);
        storedResources[1] = new Resource(4, ResourceType.SERVANT);
        storedResources[2] = new Resource(0, ResourceType.SHIELD);
        storedResources[3] = new Resource(1, ResourceType.STONE);
    }

    /*
     * Testing the method that checks if a resource is available or not in StrongBox
     */
    @Test
    public void TestCheckAvailabilityStrongBox() {

        Resource checkCoins = new Resource(1, ResourceType.COIN);
        Resource checkServants = new Resource(5, ResourceType.SERVANT);
        Resource checkShields = new Resource(1, ResourceType.SHIELD);
        Resource checkStones = new Resource(1, ResourceType.STONE);

        assertTrue(strongBox.checkAvailabilityStrongBox(checkCoins));
        assertFalse(strongBox.checkAvailabilityStrongBox(checkServants));
        assertTrue(strongBox.checkAvailabilityStrongBox(checkStones));
        assertFalse(strongBox.checkAvailabilityStrongBox(checkShields));

    }

    @Test
    public void TestAddResourceToStrongbox(){
        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        resourcesToAdd.add( new Resource(1, ResourceType.COIN));
        resourcesToAdd.add( new Resource(2, ResourceType.SERVANT));
        resourcesToAdd.add( new Resource(10, ResourceType.SHIELD));
        resourcesToAdd.add( new Resource(5, ResourceType.STONE));

        strongBox.addResourceToStrongBox(resourcesToAdd);

        assertEquals(3, strongBox.getStoredResources()[0].getQnt());
        assertEquals(6, strongBox.getStoredResources()[1].getQnt());
        assertEquals(10, strongBox.getStoredResources()[2].getQnt());
        assertEquals(6, strongBox.getStoredResources()[3].getQnt());

    }


    @Test (expected = NoSuchElementException.class)
    public void TestRemoveResourceStrongbox(){

        Resource checkCoins = new Resource(1, ResourceType.COIN);
        Resource checkServants = new Resource(5, ResourceType.SERVANT);
        Resource checkShields = new Resource(1, ResourceType.SHIELD);
        Resource checkStones = new Resource(1, ResourceType.STONE);

        strongBox.removeResourceStrongBox(checkCoins);
        assertEquals(1, strongBox.getStoredResources()[0].getQnt());

        strongBox.removeResourceStrongBox(checkShields);
        assertEquals(0, strongBox.getStoredResources()[0].getQnt());


        boolean thrown = false;
        try {
            strongBox.removeResourceStrongBox(checkServants);
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            strongBox.removeResourceStrongBox(checkShields);
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        assertTrue(thrown);

    }







}
