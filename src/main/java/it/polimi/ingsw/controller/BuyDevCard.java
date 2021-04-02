package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.devcard.DevCard;

public class BuyDevCard implements TableState{
    @Override
    public void doAction() {

    }

    public void isLegal(){}

    //returns the DevCard in row-col position of table's matrix
    //public DevCard extractCard(int row, int col){    }

    public void manageResources(){}

    //places bought card in the first free slot
    public void placeCard(DevCard card){}

    //places a new card in the row-col position of the matrix (replaces the bought one)
    public void removeAndReplaceCard(int row, int col){}


}
