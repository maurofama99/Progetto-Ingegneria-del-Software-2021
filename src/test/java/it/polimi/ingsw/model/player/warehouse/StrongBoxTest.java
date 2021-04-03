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

    private StrongBox strongBox = new StrongBox();

    /**
     Testing the method that checks if a resource is available or not in StrongBox
     **/
    @Test
    public void TestCheckAvailabilityStrongBox() {

        //creo un arraylist di risorse da aggiungere
        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        resourcesToAdd.add( new Resource(1, ResourceType.COIN));
        resourcesToAdd.add( new Resource(5, ResourceType.SERVANT));
        resourcesToAdd.add( new Resource(3, ResourceType.STONE));

        //uso il metodo addResourceToStrongBox per aggiungere l'arraylist di risorse allo strongbox
        strongBox.addResourceToStrongBox(resourcesToAdd);

        //controllo la disponibilità delle risorse appena aggiunte
        Resource oneCoin = new Resource(1, ResourceType.COIN);
        Resource sixServants = new Resource(6, ResourceType.SERVANT);
        Resource twoShields = new Resource(2, ResourceType.SHIELD);
        Resource twoStones = new Resource(2, ResourceType.STONE);

        assertTrue(strongBox.checkAvailabilityStrongBox(oneCoin));
        assertFalse(strongBox.checkAvailabilityStrongBox(sixServants));
        assertTrue(strongBox.checkAvailabilityStrongBox(twoStones));
        assertFalse(strongBox.checkAvailabilityStrongBox(twoShields));

        //TEST PASSATO

    }

    @Test
    public void TestAddResourceToStrongbox(){
        //aggiungo ulteriori risorse allo strongbox
        ArrayList<Resource> resourcesToAdd = new ArrayList<>();
        resourcesToAdd.add( new Resource(1, ResourceType.COIN));  //ora 2 coin
        resourcesToAdd.add( new Resource(2, ResourceType.SERVANT)); //ora 7 servi
        resourcesToAdd.add( new Resource(10, ResourceType.SHIELD)); //ora 10 shield
        resourcesToAdd.add( new Resource(5, ResourceType.STONE));  //ora 8 stone

        strongBox.addResourceToStrongBox(resourcesToAdd);

        assertEquals(2, strongBox.getStoredResources()[0].getQnt()); //qui c'è un problema
        assertEquals(7, strongBox.getStoredResources()[1].getQnt());
        assertEquals(10, strongBox.getStoredResources()[2].getQnt());
        assertEquals(8, strongBox.getStoredResources()[3].getQnt());

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
