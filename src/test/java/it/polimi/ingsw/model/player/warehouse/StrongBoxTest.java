package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import it.polimi.ingsw.model.player.warehouse.StrongBox;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StrongBoxTest {

    /**
     * Testing the method that checks if a resource is available or not in StrongBox
     */

    /*@Test
    public void TestCheckAvailabilityStrongBox() {

        StrongBox strongBox = new StrongBox();
        Resource[] storedResources = new Resource[4];
        storedResources[0] = new Resource(2, ResourceType.COIN);
        storedResources[1] = new Resource(4, ResourceType.SERVANT);
        storedResources[2] = new Resource(0, ResourceType.SHIELD);
        storedResources[3] = new Resource(1, ResourceType.STONE);

        Resource checkCoins = new Resource(2, ResourceType.COIN);
        Resource checkServants = new Resource(5, ResourceType.SERVANT);
        Resource checkShields = new Resource(1, ResourceType.SHIELD);
        Resource checkStones = new Resource(1, ResourceType.STONE);

        assertEquals(true, strongBox.checkAvailabilityStrongBox(checkCoins));
        assertEquals(false, strongBox.checkAvailabilityStrongBox(checkServants));
        assertEquals(true, strongBox.checkAvailabilityStrongBox(checkStones));
        assertEquals(false, strongBox.checkAvailabilityStrongBox(checkShields));


    }
*/

}
