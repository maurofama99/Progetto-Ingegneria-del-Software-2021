package it.polimi.ingsw.model.player.warehouse;

import it.polimi.ingsw.model.player.PersonalBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.leadercards.ExtraDepot;
import it.polimi.ingsw.model.player.leadercards.LeaderCard;
import it.polimi.ingsw.model.resources.Resource;
import it.polimi.ingsw.model.resources.ResourceType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;

public class DepotTest {
    private Depot depot;
    private StrongBox strongBox;
    private Warehouse warehouse;

    Resource oneCoin;
    Resource twoCoins;
    Resource twoStones;
    Resource twoShields;
    Resource threeServants;
    Resource threeStones;

    @Before
    public void setUp(){
        depot = new Depot();
        strongBox = new StrongBox();
        warehouse = new Warehouse(depot, strongBox);

        oneCoin = new Resource(1, ResourceType.COIN);
        twoCoins = new Resource(2, ResourceType.COIN);
        twoStones = new Resource(2, ResourceType.STONE);
        twoShields = new Resource(2, ResourceType.SHIELD);
        threeServants = new Resource(3, ResourceType.SERVANT);
        threeStones = new Resource(3, ResourceType.STONE);
    }
    /**
     * Testing method addResourceToDepot and switchFloors
     */
    @Test
    public void testCheckCorrectPlacement() {

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

    @Test
    public void testCheckAvailabilityDepot() throws CloneNotSupportedException {
        depot.addResourceToDepot(oneCoin, 1);
        depot.addResourceToDepot(threeStones, 3);

        ArrayList<Resource> resources = new ArrayList<>();
        resources.add(twoStones);

        assertNull(depot.checkAvailabilityDepot(resources));

        resources.add(twoCoins);
        resources.add(threeServants);

        assertEquals(2, depot.checkAvailabilityDepot(resources).size());
        assertTrue(depot.checkAvailabilityDepot(resources).get(0).getQnt()==1 &&
                depot.checkAvailabilityDepot(resources).get(0).getType().equals(ResourceType.COIN));

        assertTrue(depot.checkAvailabilityDepot(resources).get(1).getQnt()==3 &&
                depot.checkAvailabilityDepot(resources).get(1).getType().equals(ResourceType.SERVANT));
    }

    @Test
    public void testAddExtraDepot(){
        Player player = new Player("Pippo");
        player.setPersonalBoard(new PersonalBoard(warehouse));

        boolean thrown = false;
        try {
            depot.addResourceToExtraDepot(twoStones);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

        player.getPersonalBoard().getWarehouse().getDepot().getExtraFloors().set(0, Optional.of(new Resource(0, ResourceType.STONE)));
        assertTrue(depot.getExtraFloors().get(0).isPresent());

        depot.addResourceToExtraDepot(twoStones);
        assertEquals(2, depot.getExtraFloors().get(0).get().getQnt());

        thrown = false;
        try {
            depot.addResourceToExtraDepot(twoCoins);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

    }
}




