package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;


public class FloorTest{

    @Test
    public void testCheckCorrectPlacement() {

        FirstFloor fF = new FirstFloor();
        SecondFloor sF = new SecondFloor();
        ThirdFloor tF = new ThirdFloor();
        StrongBox sB = new StrongBox();
        Warehouse wH = new Warehouse(fF, sF, tF, sB);

        Resource oneCoin = new Resource(1, ResourceType.COIN);
        Resource twoCoins = new Resource(2, ResourceType.COIN);
        Resource twoStones = new Resource(2, ResourceType.STONE);
        Resource twoShields = new Resource(2, ResourceType.SHIELD);
        Resource twoServants = new Resource(2, ResourceType.SERVANT);

        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        resourcesToAdd.add(new Resource(1, ResourceType.COIN));
        resourcesToAdd.add(new Resource(2, ResourceType.SHIELD));

        //test if I can add resourcesToAdd to Floor;
        assertTrue(fF.checkCorrectPlacement(oneCoin));
        assertTrue(sF.checkCorrectPlacement(twoShields));

        //add resourcesToAdd to Floor
        wH.addResourcesToFloor(resourcesToAdd);

        //test if method differentResources works correctly
        assertFalse(tF.differentResources(twoShields));
        assertFalse(tF.differentResources(twoCoins));

        assertFalse(tF.checkCorrectPlacement(twoCoins));
        assertFalse(tF.checkCorrectPlacement(twoShields));


        assertTrue(tF.checkCorrectPlacement(twoStones));

        resourcesToAdd.add(new Resource(1, ResourceType.SERVANT));

        assertTrue(tF.checkCorrectPlacement(twoServants));





    }
}


