package it.polimi.ingsw.controller;

import java.util.ArrayList;

public class Market implements TableState{


    @Override
    public void doAction() {

    }

    //il boolean dice se l'index passato si riferisce a una riga o una colonna del market
    //public ArrayList<Resource> distributeResources(int index, boolean roworcolumn){};  //true=>row, false=>column

    //se non hai spazio nel deposito scarta la risorsa e muovi il marker degli altri giocatori
    //public void noSpaceMoveOther(ArrayList<Player> players){};

    public void resetMarketTray(){}
}
