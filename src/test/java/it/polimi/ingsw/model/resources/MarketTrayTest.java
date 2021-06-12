package it.polimi.ingsw.model.resources;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MarketTrayTest {
    MarketTray marketTray;
    Resource [][] tray;


    @Before
    public void setUp(){
        marketTray = new MarketTray();
        tray = new Resource[3][4];
        marketTray.setSlide(new Resource(1, ResourceType.FAITHPOINT));

        for (int i=0; i<3; i++) {
                tray[i][0] = new Resource(1, ResourceType.SERVANT);
                tray[i][1] = new Resource(1, ResourceType.STONE);
                tray[i][2] = new Resource(1, ResourceType.SHIELD);
                tray[i][3] = new Resource(1, ResourceType.COIN);
        }
        marketTray.setTray(tray);
    }

    @Test
      public void TestSelectRow() {
        ArrayList<Resource> firstRow = marketTray.selectRow(1);
        assertEquals(firstRow.get(0).getType(), ResourceType.SERVANT);
        assertEquals(firstRow.get(1).getType(), ResourceType.STONE);
        assertEquals(firstRow.get(2).getType(), ResourceType.SHIELD);
        assertEquals(firstRow.get(3).getType(), ResourceType.COIN);

        firstRow = marketTray.selectRow(1);
        assertEquals(firstRow.get(0).getType(), ResourceType.FAITHPOINT);
        assertEquals(firstRow.get(1).getType(), ResourceType.SERVANT);
        assertEquals(firstRow.get(2).getType(), ResourceType.STONE);
        assertEquals(firstRow.get(3).getType(), ResourceType.SHIELD);

        ArrayList<Resource> secondRow = marketTray.selectRow(2);
        assertEquals(secondRow.get(0).getType(), ResourceType.SERVANT);
        assertEquals(secondRow.get(1).getType(), ResourceType.STONE);
        assertEquals(secondRow.get(2).getType(), ResourceType.SHIELD);
        assertEquals(secondRow.get(3).getType(), ResourceType.COIN);
    }

    @Test
    public void TestSelectColumn(){
        ArrayList<Resource> firstCol = marketTray.selectColumn(1);
        assertEquals(firstCol.get(0).getType(), ResourceType.SERVANT);
        assertEquals(firstCol.get(1).getType(), ResourceType.SERVANT);
        assertEquals(firstCol.get(2).getType(), ResourceType.SERVANT);

        firstCol = marketTray.selectColumn(1);
        assertEquals(firstCol.get(0).getType(), ResourceType.FAITHPOINT);
        assertEquals(firstCol.get(1).getType(), ResourceType.SERVANT);
        assertEquals(firstCol.get(2).getType(), ResourceType.SERVANT);
    }

    @Test
    public void testCreateMarbles(){
        ArrayList<Resource> marbles = marketTray.createMarbles();
        assertEquals(13, marbles.size());
        int counter=0;
        for (int i = 0; i<marbles.size(); i++){
            if (marbles.get(i).getType()==ResourceType.WHITERESOURCE)
                counter++;
        }

        assertEquals(4, counter);


    }





}