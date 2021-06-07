package it.polimi.ingsw.model.resources;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MarketTrayTest {
    MarketTray marketTray;
    private Resource[][] tray;
    private Resource slide;

    /*
    @Before
    public void setUp(){
        marketTray = new MarketTray();
        tray = new Resource[3][4];
        slide = new Resource(1, ResourceType.FAITHPOINT);

        for (int i=0; i<3; i++) {
                tray[i][0] = new Resource(1, ResourceType.SERVANT);
                tray[i][1] = new Resource(1, ResourceType.STONE);
                tray[i][2] = new Resource(1, ResourceType.SHIELD);
                tray[i][3] = new Resource(1, ResourceType.COIN);
        }
    }

    @Test
      public void TestSelectRow() {
        ArrayList<Resource> firstRow = marketTray.selectRow(1);
        assertTrue(firstRow.get(0).getType().equals(ResourceType.SERVANT));
        assertTrue(firstRow.get(1).getType().equals(ResourceType.STONE));
        assertTrue(firstRow.get(2).getType().equals(ResourceType.SHIELD));
    }

     */


}