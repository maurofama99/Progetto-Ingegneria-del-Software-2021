package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DepotTest {

    @Test
    public void testCheckCorrectPlacement() {

        Depot depot = new Depot();
        StrongBox sB = new StrongBox();
        Warehouse wH = new Warehouse(depot, sB);

        Resource oneCoin = new Resource(1, ResourceType.COIN);
        Resource twoCoins = new Resource(2, ResourceType.COIN);
        Resource twoStones = new Resource(2, ResourceType.STONE);
        Resource twoShields = new Resource(2, ResourceType.SHIELD);
        Resource twoServants = new Resource(2, ResourceType.SERVANT);

        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        resourcesToAdd.add(new Resource(1, ResourceType.COIN));
        resourcesToAdd.add(new Resource(2, ResourceType.SHIELD));

        //test if I can add resourcesToAdd to Floor;


        //add resourcesToAdd to Floor




    }
}




