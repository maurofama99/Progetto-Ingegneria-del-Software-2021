package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.player.*;
import it.polimi.ingsw.model.resources.*;

import java.util.ArrayList;

public class Market implements TableState{


    @Override
    public void doAction() {

    }

    //boolean says if given index is for row or column of the market
    public ArrayList<Resource> distributeResources(int index, boolean roworcolumn){
        return null;
    }  //true=>row, false=>column

    //no space in warehouse, discard resource and moves players' markers
    public void noSpaceMoveOther(ArrayList<Player> players){}

    public void resetMarketTray(){}
}
