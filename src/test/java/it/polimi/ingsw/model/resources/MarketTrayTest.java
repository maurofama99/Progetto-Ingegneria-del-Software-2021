package it.polimi.ingsw.model.resources;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MarketTrayTest {

    /*@Test
      public void TestSelectRow() {
        int i;
        int j;
        Resource[][] tray = new Resource[3][4];
        Resource slider = new Resource(1, ResourceType.SHIELD);
        MarketTray market = new MarketTray(tray,slider);

        //initialize a random market tray
        for (i=0; i<3; i++){
            for(j=0; j<4; j++){
                if (i%3==0) market.getTray()[i][j]= new Resource(1,ResourceType.SERVANT);
                else if (j%4==0) market.getTray()[i][j]= new Resource(1,ResourceType.STONE);
                else if (j%2==0) market.getTray()[i][j]= new Resource(1,ResourceType.FAITHPOINT);
                else if (j%3==0) market.getTray()[i][j]= new Resource(1,ResourceType.COIN);
                else if (j%7==0) market.getTray()[i][j]= new Resource(1,ResourceType.WHITERESOURCE);
                else if (i%2==0) market.getTray()[i][j]= new Resource(1,ResourceType.WHITERESOURCE);
                else market.getTray()[i][j]= new Resource(1,ResourceType.COIN);
                System.out.print(market.getTray()[i][j].toString() + " ");
                //SERVANT SERVANT SERVANT SERVANT
                //STONE COIN FAITHPOINT COIN
                //STONE WHITE FAITHPOINT COIN
            }
            System.out.println("");
        }

        ArrayList<Resource> result = market.selectColumn(2);

        System.out.println("");
        //stampa nuovo market
        for (i=0; i<3; i++){
            for(j=0; j<4; j++){
                System.out.print(market.getTray()[i][j].toString() + " ");
                //SERVANT SERVANT SERVANT SERVANT
                //STONE COIN FAITHPOINT COIN
                //STONE WHITE FAITHPOINT COIN
            }
            System.out.println("");
        }

        System.out.println("");
        //stampa risultato
        for(i=0; i<3; i++){
            System.out.print(result.get(i) + " ");
        }

        System.out.println("");
        //stampa slider
        System.out.println(market.getSlide());
    }   */

}