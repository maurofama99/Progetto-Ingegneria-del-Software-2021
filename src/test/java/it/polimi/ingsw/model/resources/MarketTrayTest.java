package it.polimi.ingsw.model.resources;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MarketTrayTest {

    @Test
      public void TestSelectRow() {
        int i;
        int j;
        Resource[][] tray = new Resource[3][4];
        Resource slider = new Resource(1, ResourceType.SHIELD);
        MarketTray market = new MarketTray();

        //initialize a random market tray
        for (i=0; i<3; i++){
            for(j=0; j<4; j++){
                System.out.print(market.getTray()[i][j].toString() + " ");
            }
            System.out.println();
        }

        System.out.println("Slider: " + slider);


    }

}