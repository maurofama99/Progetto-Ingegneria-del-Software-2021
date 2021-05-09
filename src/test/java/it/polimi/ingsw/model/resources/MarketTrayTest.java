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
                //SERVANT SERVANT SERVANT SERVANT
                //STONE COIN FAITHPOINT COIN
                //STONE WHITE FAITHPOINT COIN
            }
            System.out.println();
        }

        System.out.println(slider);

        /*ArrayList<Resource> result = market.selectRow(3);

        System.out.println();
        //stampa nuovo market
        for (i=0; i<3; i++){
            for(j=0; j<4; j++){
                System.out.print(market.getTray()[i][j].toString() + " ");
                //SERVANT SERVANT SERVANT SERVANT
                //STONE COIN FAITHPOINT COIN
                //STONE WHITE FAITHPOINT COIN
            }
            System.out.println();
        }

        System.out.println();
        //stampa risultato
        for(i=0; i<3; i++){
            System.out.print(result.get(i) + " ");
        }

        System.out.println();
        //stampa slider
        System.out.println(market.getSlide());*/
    }

}